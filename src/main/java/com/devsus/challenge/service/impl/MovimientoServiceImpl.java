package com.devsus.challenge.service.impl;

import com.devsus.challenge.entity.ClienteEntity;
import com.devsus.challenge.exception.InsufficientBalanceException;
import com.devsus.challenge.exception.MaxExtractionPerDayException;
import com.devsus.challenge.utility.MovimientoTypeEnum;
import com.devsus.challenge.dto.request.MovimientoRequest;
import com.devsus.challenge.dto.response.DeleteResponse;
import com.devsus.challenge.dto.response.MovimientoResponse;
import com.devsus.challenge.dto.response.SaveResponse;
import com.devsus.challenge.entity.CuentaEntity;
import com.devsus.challenge.entity.MovimientoEntity;
import com.devsus.challenge.exception.IdNotFoundException;
import com.devsus.challenge.mapper.MovimientoMapper;
import com.devsus.challenge.repository.CuentaRepository;
import com.devsus.challenge.repository.MovimientoRepository;
import com.devsus.challenge.service.MovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Map;
import java.util.Set;

@Service
@Transactional
public class MovimientoServiceImpl implements MovimientoService {

    @Autowired
    private MovimientoRepository movimientoRepository;

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private MovimientoMapper mapper;

    @Override
    public SaveResponse save(MovimientoRequest request) {


        MovimientoResponse response = mapper.entityToResponse(
                                                movimientoRepository.save(
                                                                    mapper.requestToEntity(new CuentaEntity(), request, 0L)));

        return SaveResponse.builder()
                .saved(response)
                .message("Movimiento guardado con éxito")
                .build();
    }

    @Override
    public MovimientoResponse getById(Long id) {

        MovimientoEntity entity = movimientoRepository.findById(id).orElseThrow(() -> new IdNotFoundException(id));

        return mapper.entityToResponse(entity);
    }

    @Override
    public SaveResponse update(Long id, MovimientoRequest request) {

        MovimientoEntity entity = movimientoRepository.findById(id).orElseThrow(() -> new IdNotFoundException(id));
        MovimientoResponse response = mapper.entityToResponse(
                                            movimientoRepository.save(
                                                        mapper.updateEntity(entity, request, 0L)));

        return SaveResponse.builder()
                .saved(response)
                .message(String.format("Movimiento con id '%s' actualizado con éxito", id))
                .build();
    }

    @Override
    public DeleteResponse delete(Long id) {

        movimientoRepository.findById(id).orElseThrow(() -> new IdNotFoundException(id));
        movimientoRepository.deleteById(id);

        return DeleteResponse.builder()
                .message(String.format("Movimiento con id '%s' eliminado con éxito", id))
                .build();
    }

    @Override
    public SaveResponse partialUpdate(Map<String, Object> update, Long id) {

        MovimientoEntity movimiento = movimientoRepository.findById(id).orElseThrow(() -> new IdNotFoundException(id));

        return SaveResponse.builder()
                .saved(mapper.entityToResponse(
                        movimientoRepository.save(
                                mapper.partialUpdateToEntity(update, movimiento))))
                .message("Movimiento actualizado con éxito")
                .build();
    }
}