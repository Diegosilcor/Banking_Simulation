package com.devsus.challenge.controller;

import com.devsus.challenge.dto.request.MovimientoRequest;
import com.devsus.challenge.dto.response.MovimientoResponse;
import com.devsus.challenge.dto.response.DeleteResponse;
import com.devsus.challenge.dto.response.SaveResponse;
import com.devsus.challenge.service.MovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/movimientos")
public class MovimientoController {

    @Autowired
    private MovimientoService movimientoService;

    @PostMapping
    public ResponseEntity<SaveResponse> saveMovimiento(@RequestBody MovimientoRequest movimientoRequest){

        return ResponseEntity.status(HttpStatus.OK).body(movimientoService.save(movimientoRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovimientoResponse> readMovimiento(@PathVariable Long id){

        return ResponseEntity.status(HttpStatus.OK).body(movimientoService.getById(id));

    }

    @PutMapping("/{id}")
    public ResponseEntity<SaveResponse> updateMovimiento(@PathVariable Long id, @RequestBody MovimientoRequest movimientoRequest){

        return ResponseEntity.status(HttpStatus.OK).body(movimientoService.update(id, movimientoRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteResponse> deleteMovimiento(@PathVariable Long id){

        return ResponseEntity.status(HttpStatus.OK).body(movimientoService.delete(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<SaveResponse> partialUpdate(
            @RequestBody Map<String, Object> update,
            @PathVariable Long id) {

        return ResponseEntity.status(HttpStatus.OK).body(movimientoService.partialUpdate(update, id));
    }
}
