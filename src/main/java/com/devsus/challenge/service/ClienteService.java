package com.devsus.challenge.service;

import com.devsus.challenge.dto.request.ClienteRequest;
import com.devsus.challenge.dto.request.CuentaRequest;
import com.devsus.challenge.dto.response.ClienteResponse;
import com.devsus.challenge.dto.response.DeleteResponse;
import com.devsus.challenge.dto.response.ReporteResponse;
import com.devsus.challenge.dto.response.SaveResponse;
import org.springframework.http.ResponseEntity;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Map;
import java.util.Set;

public interface ClienteService {
    SaveResponse save(ClienteRequest clienteRequest);

    ClienteResponse getById(Long id);

    SaveResponse update(Long id, ClienteRequest clienteRequest);

    DeleteResponse delete(Long id);

    Set<ReporteResponse> getReporte(Long id, String fechaInicial, String fechaFinal);

    SaveResponse createCuenta(Long id, CuentaRequest request);

    SaveResponse partialUpdate(Map<String, Object> update, Long id);
}
