package com.devsus.challenge.controller;

import com.devsus.challenge.dto.request.ClienteRequest;
import com.devsus.challenge.dto.request.CuentaRequest;
import com.devsus.challenge.dto.response.ClienteResponse;
import com.devsus.challenge.dto.response.DeleteResponse;
import com.devsus.challenge.dto.response.ReporteResponse;
import com.devsus.challenge.dto.response.SaveResponse;
import com.devsus.challenge.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    public ResponseEntity<SaveResponse> saveCliente(@RequestBody ClienteRequest clienteRequest){

        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.save(clienteRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponse> readCliente(@PathVariable Long id){

        return ResponseEntity.status(HttpStatus.OK).body(clienteService.getById(id));

    }

    @PutMapping("/{id}")
    public ResponseEntity<SaveResponse> updateCliente(@PathVariable Long id, @RequestBody ClienteRequest clienteRequest){

        return ResponseEntity.status(HttpStatus.OK).body(clienteService.update(id, clienteRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteResponse> deleteCliente(@PathVariable Long id){

        return ResponseEntity.status(HttpStatus.OK).body(clienteService.delete(id));
    }

    @GetMapping("/{id}/reporte")
    public ResponseEntity<Set<ReporteResponse>> getReporte(@PathVariable Long id,
                                                           @RequestParam(value = "fecha_inicial") String fechaInicial,
                                                           @RequestParam(value = "fecha_final") String fechaFinal){

        return ResponseEntity.status(HttpStatus.OK).body(clienteService.getReporte(id, fechaInicial, fechaFinal));

    }

    @PostMapping("/{id}/cuentas")
    public ResponseEntity<SaveResponse> createCuenta(@PathVariable Long id, @RequestBody CuentaRequest request){

        return ResponseEntity.status(HttpStatus.OK).body(clienteService.createCuenta(id, request));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<SaveResponse> partialUpdate(
                                                    @RequestBody Map<String, Object> update,
                                                    @PathVariable Long id) {

        return ResponseEntity.status(HttpStatus.OK).body(clienteService.partialUpdate(update, id));
    }
}
