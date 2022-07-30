package com.devsus.challenge.service;

import com.devsus.challenge.dto.request.MovimientoRequest;
import com.devsus.challenge.dto.response.DeleteResponse;
import com.devsus.challenge.dto.response.MovimientoResponse;
import com.devsus.challenge.dto.response.SaveResponse;

import java.util.Map;

public interface MovimientoService {

    SaveResponse save(MovimientoRequest movimientoRequest);

    MovimientoResponse getById(Long id);

    SaveResponse update(Long id, MovimientoRequest movimientoRequest);

    DeleteResponse delete(Long id);

    SaveResponse partialUpdate(Map<String, Object> update, Long id);
}
