package com.devsus.challenge.mapper;

import com.devsus.challenge.dto.request.CuentaRequest;
import com.devsus.challenge.dto.response.CuentaResponse;
import com.devsus.challenge.entity.ClienteEntity;
import com.devsus.challenge.entity.CuentaEntity;
import com.devsus.challenge.utility.CuentaTypeEnum;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;

@Component
public class CuentaMapper {

    public CuentaEntity requestToEntity(ClienteEntity cliente, CuentaRequest request) {

        return CuentaEntity.builder()
                .cliente(cliente)
                .status(request.getStatus())
                .type(request.getType())
                .balance(request.getBalance())
                .softDelete(Boolean.FALSE)
                .build();
    }

    public CuentaResponse entityToResponse(CuentaEntity entity) {

        return CuentaResponse.builder()
                .cuentaId(entity.getCuentaId())
                .status(entity.getStatus())
                .type(entity.getType())
                .balance(entity.getBalance())
                .build();
    }

    public CuentaEntity updateEntity(CuentaEntity entity, CuentaRequest request) {

        entity.setStatus(request.getStatus());
        entity.setType(request.getType());
        entity.setBalance(request.getBalance());

        return entity;
    }

    public CuentaEntity partialUpdateToEntity(Map<String, Object> update, CuentaEntity cuenta) {

        update.forEach((key, value) -> {

            Object newValue;

            Field field = ReflectionUtils.findField(CuentaEntity.class, key);
            field.setAccessible(true);

            if(value.getClass().equals(Integer.class)){
                String stringValue = String.valueOf(value);
                newValue = Long.valueOf(stringValue);
            } else {
                newValue = value;
            }

            if(key.equals("type")){
                switch (String.valueOf(value)){
                    case "AHORRO":
                        newValue = CuentaTypeEnum.AHORRO;
                        break;
                    case "CORRIENTE":
                        newValue = CuentaTypeEnum.CORRIENTE;
                        break;
                }
            }

            ReflectionUtils.setField(field, cuenta, newValue);

        });

        return cuenta;
    }
}
