package com.devsus.challenge.service;

import com.devsus.challenge.dto.request.CuentaRequest;
import com.devsus.challenge.dto.request.MovimientoRequest;
import com.devsus.challenge.dto.response.CuentaResponse;
import com.devsus.challenge.dto.response.DeleteResponse;
import com.devsus.challenge.dto.response.SaveResponse;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface CuentaService {

    SaveResponse save(CuentaRequest cuentaRequest);

    CuentaResponse getById(Long id);

    SaveResponse update(Long id, CuentaRequest cuentaRequest);

    DeleteResponse delete(Long id);

    SaveResponse createMovimiento(Long id, MovimientoRequest movimientoRequest);

    SaveResponse partialUpdate(Map<String, Object> update, Long id);
}
