package com.devsus.challenge.mapper;

import com.devsus.challenge.dto.request.MovimientoRequest;
import com.devsus.challenge.dto.response.MovimientoResponse;
import com.devsus.challenge.entity.ClienteEntity;
import com.devsus.challenge.entity.CuentaEntity;
import com.devsus.challenge.entity.MovimientoEntity;
import com.devsus.challenge.utility.CuentaTypeEnum;
import com.devsus.challenge.utility.MovimientoTypeEnum;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

@Component
public class MovimientoMapper {


    public MovimientoEntity requestToEntity(CuentaEntity cuenta, MovimientoRequest request, Long newAmount) {

        Calendar calendar = Calendar.getInstance();


        return MovimientoEntity.builder()
                .cuenta(cuenta)
                .date(new Timestamp(calendar.getTimeInMillis()))
                .type(request.getType())
                .amount(request.getAmount())
                .final_balance(newAmount)
                .softDelete(Boolean.FALSE)
                .build();
    }

    public MovimientoResponse entityToResponse(MovimientoEntity entity) {

        return MovimientoResponse.builder()
                .movimientoId(entity.getMovimientoId())
                .date(entity.getDate())
                .type(entity.getType())
                .amount(entity.getAmount())
                .final_balance(entity.getFinal_balance())
                .build();
    }

    public MovimientoEntity updateEntity(MovimientoEntity entity, MovimientoRequest request, Long final_balance) {

        entity.setType(request.getType());
        entity.setAmount(request.getAmount());
        entity.setFinal_balance(final_balance);

        return entity;
    }

    public MovimientoEntity partialUpdateToEntity(Map<String, Object> update, MovimientoEntity movimiento) {

        update.forEach((key, value) -> {

            Object newValue;

            Field field = ReflectionUtils.findField(MovimientoEntity.class, key);
            field.setAccessible(true);

            if(value.getClass().equals(Integer.class)){
                String stringValue = String.valueOf(value);
                newValue = Long.valueOf(stringValue);
            } else {
                newValue = value;
            }

            if(key.equals("type")){
                switch (String.valueOf(value)){
                    case "DEBITO":
                        newValue = MovimientoTypeEnum.DEBITO;
                        break;
                    case "DEPOSITO":
                        newValue = MovimientoTypeEnum.DEPOSITO;
                        break;
                }
            }

            ReflectionUtils.setField(field, movimiento, newValue);

        });

        return movimiento;
    }
}
