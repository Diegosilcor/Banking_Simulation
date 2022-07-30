package com.devsus.challenge.mapper;

import com.devsus.challenge.dto.request.ClienteRequest;
import com.devsus.challenge.dto.response.ClienteResponse;
import com.devsus.challenge.entity.ClienteEntity;
import lombok.val;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;

@Component
public class ClienteMapper {

    public ClienteEntity requestToEntity(ClienteRequest request) {

        ClienteEntity entity = new ClienteEntity();
        entity.setStatus(request.getStatus());
        entity.setPassword(request.getPassword());
        entity.setName(request.getName());
        entity.setGender(request.getGender());
        entity.setAge(request.getAge());
        entity.setAddress(request.getAddress());
        entity.setPhone(request.getPhone());
        entity.setDni(request.getDni());

        return entity;
    }

    public ClienteResponse entityToResponse(ClienteEntity entity) {

        return ClienteResponse.builder()
                .clienteId(entity.getClienteId())
                .status(entity.getStatus())
                .name(entity.getName())
                .gender(entity.getGender())
                .age(entity.getAge())
                .address(entity.getAddress())
                .phone(entity.getPhone())
                .dni(entity.getDni())
                .build();
    }

    public ClienteEntity updateEntity(ClienteEntity entity, ClienteRequest request) {

        entity.setStatus(request.getStatus());
        entity.setName(request.getName());
        entity.setGender(request.getGender());
        entity.setAge(request.getAge());
        entity.setAddress(request.getAddress());
        entity.setPhone(request.getPhone());
        entity.setDni(request.getDni());

        return entity;
    }

    public ClienteEntity partialUpdateToEntity(Map<String, Object> update, ClienteEntity cliente) {

        update.forEach((key, value) -> {

            Object newValue;

            Field field = ReflectionUtils.findField(ClienteEntity.class, key);
            field.setAccessible(true);

            if(value.getClass().equals(Integer.class)){
                String stringValue = String.valueOf(value);
                newValue = Long.valueOf(stringValue);
            } else {
                newValue = value;
            }
            ReflectionUtils.setField(field, cliente, newValue);

        });

        return cliente;
    }
}
