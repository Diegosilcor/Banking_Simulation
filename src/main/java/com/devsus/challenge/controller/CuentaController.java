package com.devsus.challenge.controller;

import com.devsus.challenge.dto.request.CuentaRequest;
import com.devsus.challenge.dto.request.MovimientoRequest;
import com.devsus.challenge.dto.response.CuentaResponse;
import com.devsus.challenge.dto.response.DeleteResponse;
import com.devsus.challenge.dto.response.SaveResponse;
import com.devsus.challenge.exception.IdNotFoundException;
import com.devsus.challenge.exception.InsufficientBalanceException;
import com.devsus.challenge.exception.MaxExtractionPerDayException;
import com.devsus.challenge.service.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/cuentas")
public class CuentaController {

    @Autowired
    private CuentaService cuentaService;

    @PostMapping
    public ResponseEntity<SaveResponse> saveCuenta(@RequestBody CuentaRequest cuentaRequest){

        return ResponseEntity.status(HttpStatus.OK).body(cuentaService.save(cuentaRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CuentaResponse> readCuenta(@PathVariable Long id){

        return ResponseEntity.status(HttpStatus.OK).body(cuentaService.getById(id));

    }

    @PutMapping("/{id}")
    public ResponseEntity<SaveResponse> updateCuenta(@PathVariable Long id, @RequestBody CuentaRequest cuentaRequest){

        return ResponseEntity.status(HttpStatus.OK).body(cuentaService.update(id, cuentaRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteResponse> deleteCuenta(@PathVariable Long id){

        return ResponseEntity.status(HttpStatus.OK).body(cuentaService.delete(id));
    }

    @PostMapping("/{id}/movimientos")
    public ResponseEntity<SaveResponse> createMovimiento(@PathVariable Long id, @RequestBody MovimientoRequest movimientoRequest){

        return ResponseEntity.status(HttpStatus.OK).body(cuentaService.createMovimiento(id, movimientoRequest));

    }

    @PatchMapping("/{id}")
    public ResponseEntity<SaveResponse> partialUpdate(
                                                    @RequestBody Map<String, Object> update,
                                                    @PathVariable Long id) {

        return ResponseEntity.status(HttpStatus.OK).body(cuentaService.partialUpdate(update, id));
    }
}
