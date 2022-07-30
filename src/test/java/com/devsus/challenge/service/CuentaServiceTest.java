package com.devsus.challenge.service;

import com.devsus.challenge.dto.request.MovimientoRequest;
import com.devsus.challenge.entity.CuentaEntity;
import com.devsus.challenge.entity.MovimientoEntity;
import com.devsus.challenge.exception.IdNotFoundException;
import com.devsus.challenge.exception.InsufficientBalanceException;
import com.devsus.challenge.exception.MaxExtractionPerDayException;
import com.devsus.challenge.repository.CuentaRepository;
import com.devsus.challenge.service.CuentaService;
import com.devsus.challenge.service.impl.CuentaServiceImpl;
import com.devsus.challenge.utility.CuentaTypeEnum;
import com.devsus.challenge.utility.MovimientoTypeEnum;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Timestamp;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CuentaServiceTest {

    @InjectMocks
    CuentaServiceImpl cuentaServiceImpl;

    @Mock
    private CuentaRepository cuentaRepository;


    @Test(expected = InsufficientBalanceException.class)
    public void when_makingDebitGreaterThanBalance_then_throwsException() {

        Long id = 1L;

        MovimientoRequest request = new MovimientoRequest();
        request.setType(MovimientoTypeEnum.DEBITO);
        request.setAmount(500L);

        CuentaEntity cuenta = new CuentaEntity();
        cuenta.setBalance(400L);
        cuenta.setStatus(true);
        cuenta.setCuentaId(1L);
        cuenta.setType(CuentaTypeEnum.AHORRO);
        cuenta.setMovimientos(null);

        when(cuentaRepository.findById(id))
               .thenReturn(Optional.of(cuenta));

        cuentaServiceImpl.createMovimiento(id, request);
    }

    @Test(expected = MaxExtractionPerDayException.class)
    public void when_extractingMoreThanAllowedPerDay_then_throwsException(){

        Long oneHourAgo = Calendar.getInstance().getTimeInMillis()-(1000*60*60);
        Long id = 1L;

        MovimientoRequest request = new MovimientoRequest();
        request.setType(MovimientoTypeEnum.DEBITO);
        request.setAmount(500L);

        MovimientoEntity movimiento = new MovimientoEntity();
        movimiento.setDate(new Timestamp(oneHourAgo));
        movimiento.setType(MovimientoTypeEnum.DEBITO);
        movimiento.setAmount(600L);

        CuentaEntity cuenta = new CuentaEntity();
        cuenta.setBalance(600L);
        cuenta.setStatus(true);
        cuenta.setCuentaId(1L);
        cuenta.setType(CuentaTypeEnum.AHORRO);

        Set<MovimientoEntity> nuevosMovimientos = new HashSet<>();
        nuevosMovimientos.add(movimiento);

        cuenta.setMovimientos(nuevosMovimientos);

        when(cuentaRepository.findById(id))
                .thenReturn(Optional.of(cuenta));

        cuentaServiceImpl.createMovimiento(id, request);
    }
}
