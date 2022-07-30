package com.devsus.challenge.service.impl;

import com.devsus.challenge.dto.request.CuentaRequest;
import com.devsus.challenge.dto.request.MovimientoRequest;
import com.devsus.challenge.dto.response.CuentaResponse;
import com.devsus.challenge.dto.response.DeleteResponse;
import com.devsus.challenge.dto.response.SaveResponse;
import com.devsus.challenge.entity.ClienteEntity;
import com.devsus.challenge.entity.CuentaEntity;
import com.devsus.challenge.entity.MovimientoEntity;
import com.devsus.challenge.exception.IdNotFoundException;
import com.devsus.challenge.exception.InsufficientBalanceException;
import com.devsus.challenge.exception.MaxExtractionPerDayException;
import com.devsus.challenge.mapper.CuentaMapper;
import com.devsus.challenge.mapper.MovimientoMapper;
import com.devsus.challenge.repository.CuentaRepository;
import com.devsus.challenge.service.CuentaService;
import com.devsus.challenge.utility.MovimientoTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Map;
import java.util.Set;

@Service
@Transactional
public class CuentaServiceImpl implements CuentaService {

    private static final Long MAX_DEBITS_PER_DAY = 1000L;

    public CuentaServiceImpl(CuentaRepository cuentaRepository){
        this.cuentaRepository = cuentaRepository;
    }

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private CuentaMapper cuentaMapper;

    @Autowired
    private MovimientoMapper movimientoMapper;

    @Override
    public SaveResponse save(CuentaRequest request) {
        CuentaEntity entity = cuentaMapper.requestToEntity(new ClienteEntity(), request);
        cuentaRepository.save(entity);
        CuentaResponse response = cuentaMapper.entityToResponse(entity);

        return SaveResponse.builder()
                            .saved(response)
                            .message("Cuenta guardada con éxito")
                            .build();
    }

    @Override
    public CuentaResponse getById(Long id) {

        CuentaEntity entity = cuentaRepository.findById(id).orElseThrow(() -> new IdNotFoundException(id));

        return cuentaMapper.entityToResponse(entity);
    }

    @Override
    public SaveResponse update(Long id, CuentaRequest cuentaRequest) {

        CuentaEntity entity = cuentaRepository.findById(id).orElseThrow(() -> new IdNotFoundException(id));
        CuentaResponse response = cuentaMapper.entityToResponse(
                                            cuentaRepository.save(
                                                        cuentaMapper.updateEntity(entity, cuentaRequest)));

        return SaveResponse.builder()
                .saved(response)
                .message(String.format("Cuenta con id '%s' actualizada con éxito", id))
                .build();
    }

    @Override
    public DeleteResponse delete(Long id) {

        cuentaRepository.findById(id).orElseThrow(() -> new IdNotFoundException(id));
        cuentaRepository.deleteById(id);

        return DeleteResponse.builder()
                .message(String.format("Cuenta con id '%s' eliminada con éxito", id))
                .build();
    }

    @Override
    public SaveResponse createMovimiento(Long id, MovimientoRequest movimientoRequest) {

        CuentaEntity cuenta = cuentaRepository.findById(id).orElseThrow(() -> new IdNotFoundException(id));

        if(movimientoRequest.getType().equals(MovimientoTypeEnum.DEBITO)){

            validateSufficientBalance(cuenta, movimientoRequest);
            validateMaxExtractionPerDay(cuenta, movimientoRequest);
        }

        Long finalBalance = finalBalance(cuenta, movimientoRequest);
        MovimientoEntity movimiento = movimientoMapper.requestToEntity(cuenta, movimientoRequest, finalBalance);
        cuenta.getMovimientos().add(movimiento);
        cuenta.setBalance(finalBalance);
        cuentaRepository.save(cuenta);

        return SaveResponse.builder()
                .saved(movimientoMapper.entityToResponse(movimiento))      // Queda Id vacía porque todavía no se guardó
                                                                           // Cómo lo busco para mappearlo, si no sé su Id?
                .message("Movimiento creado y asignado a la cuenta con éxito")
                .build();
    }

    @Override
    public SaveResponse partialUpdate(Map<String, Object> update, Long id) {
        CuentaEntity cuenta = cuentaRepository.findById(id).orElseThrow(() -> new IdNotFoundException(id));

        return SaveResponse.builder()
                .saved(cuentaMapper.entityToResponse(
                        cuentaRepository.save(
                                cuentaMapper.partialUpdateToEntity(update, cuenta))))
                .message("Cuenta actualizada con éxito")
                .build();
    }

    //----------------------------MÉTODOS INTERNOS--------------------------------------------------------------------

    private void validateSufficientBalance(CuentaEntity cuenta, MovimientoRequest request){

        Long initial_balance = cuenta.getBalance();
        Long newAmount = request.getAmount();

        if(initial_balance < newAmount){
            throw new InsufficientBalanceException(initial_balance, newAmount);
        }
    }

    private void validateMaxExtractionPerDay(CuentaEntity cuenta, MovimientoRequest request){

        Long totalAmountToday = 0L;

        Set<MovimientoEntity> movimientos = cuenta.getMovimientos();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND,0);

        Long today = calendar.getTimeInMillis();

        for(MovimientoEntity movimiento : movimientos){

            Long then = movimiento.getDate().getTime();

            if(then >= today && movimiento.getType().equals(MovimientoTypeEnum.DEBITO)){
                totalAmountToday += movimiento.getAmount();
            }
        }

        if(totalAmountToday + request.getAmount() > MAX_DEBITS_PER_DAY){
            throw new MaxExtractionPerDayException();
        }
    }

    private Long finalBalance(CuentaEntity cuenta, MovimientoRequest movimiento){

        Long finalBalance = 0L;

        if(movimiento.getType().equals(MovimientoTypeEnum.DEBITO)){

            finalBalance = cuenta.getBalance() - movimiento.getAmount();

        } else if (movimiento.getType().equals(MovimientoTypeEnum.DEPOSITO)){

            finalBalance = cuenta.getBalance() + movimiento.getAmount();
        }
        return finalBalance;
    }
}
