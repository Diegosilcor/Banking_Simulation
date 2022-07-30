package com.devsus.challenge.service.impl;

import com.devsus.challenge.dto.request.ClienteRequest;
import com.devsus.challenge.dto.request.CuentaRequest;
import com.devsus.challenge.dto.response.*;
import com.devsus.challenge.entity.ClienteEntity;
import com.devsus.challenge.entity.CuentaEntity;
import com.devsus.challenge.entity.MovimientoEntity;
import com.devsus.challenge.exception.IdNotFoundException;
import com.devsus.challenge.mapper.ClienteMapper;
import com.devsus.challenge.mapper.CuentaMapper;
import com.devsus.challenge.mapper.MovimientoMapper;
import com.devsus.challenge.repository.ClienteRepository;
import com.devsus.challenge.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ClienteMapper clienteMapper;

    @Autowired
    private CuentaMapper cuentaMapper;

    @Autowired
    private MovimientoMapper movimientoMapper;

    @Override
    public SaveResponse save(ClienteRequest clienteRequest) {

        return SaveResponse.builder()
                        .saved(clienteMapper.entityToResponse(
                                            clienteRepository.save(
                                                                clienteMapper.requestToEntity(clienteRequest))))
                        .message("Cliente guardado con éxito")
                        .build();
    }

    @Override
    public ClienteResponse getById(Long id) {

        ClienteEntity entity = clienteRepository.findById(id).orElseThrow(() -> new IdNotFoundException(id));

        return clienteMapper.entityToResponse(entity);
    }

    @Override
    public SaveResponse update(Long id, ClienteRequest clienteRequest) {

        ClienteEntity entity = clienteRepository.findById(id).orElseThrow(() -> new IdNotFoundException(id));
        ClienteResponse response = clienteMapper.entityToResponse(
                                                clienteRepository.save(
                                                                    clienteMapper.updateEntity(entity, clienteRequest)));

        return SaveResponse.builder()
                .saved(response)
                .message(String.format("Cliente con id '%s' actualizado con éxito", id))
                .build();

    }

    @Override
    public DeleteResponse delete(Long id) {

        clienteRepository.findById(id).orElseThrow(() -> new IdNotFoundException(id));
        clienteRepository.deleteById(id);

        return DeleteResponse.builder()
                .message(String.format("Cliente con id '%s' eliminado con éxito", id))
                .build();
    }

    @Override
    public Set<ReporteResponse> getReporte(Long id, String fechaInicial, String fechaFinal) {

        Long fInicial = fechaStringToMillis(fechaInicial);
        Long fFinal = fechaStringToMillis(fechaFinal);

        Set<ReporteResponse> response = new HashSet<>();

        ClienteEntity cliente = clienteRepository.findById(id).orElseThrow(() -> new IdNotFoundException(id));

        Set<CuentaEntity> cuentas = cliente.getCuentas();

        for(CuentaEntity cuenta : cuentas){

            Set<MovimientoEntity> movimientos = cuenta.getMovimientos();

            for(MovimientoEntity movimiento : movimientos) {

                Long then = movimiento.getDate().getTime();

                if(then >= fInicial && then < fFinal){

                    response.add(ReporteResponse.builder()
                                                .cuenta_id(cuenta.getCuentaId())
                                                .balance(cuenta.getBalance())
                                                .movimiento(movimientoMapper.entityToResponse(movimiento))
                                                .build());
                }
            }
        }
        return response;
    }

    @Override
    public SaveResponse createCuenta(Long id, CuentaRequest request) {

        ClienteEntity cliente = clienteRepository.findById(id).orElseThrow(() -> new IdNotFoundException(id));
        CuentaEntity cuenta = cuentaMapper.requestToEntity(cliente, request);

        cliente.getCuentas().add(cuenta);
        clienteRepository.save(cliente);

        return SaveResponse.builder()
                .saved(cuentaMapper.entityToResponse(cuenta))
                .message("Cuenta creada y asignada al cliente con éxito")
                .build();
    }

    @Override
    public SaveResponse partialUpdate(Map<String, Object> update, Long id) {
        ClienteEntity cliente = clienteRepository.findById(id).orElseThrow(() -> new IdNotFoundException(id));

        return SaveResponse.builder()
                .saved(clienteMapper.entityToResponse(
                                    clienteRepository.save(
                                                            clienteMapper.partialUpdateToEntity(update, cliente))))
                .message("Cliente actualizado con éxito")
                .build();
    }

    //---------------------------------Métodos privados-----------------------------------------------------------------

    private Long fechaStringToMillis(String fechaString){

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try{
            calendar.setTime(sdf.parse(fechaString));

        } catch(ParseException e){
            e.printStackTrace();
        }

        return calendar.getTimeInMillis();
    }
}