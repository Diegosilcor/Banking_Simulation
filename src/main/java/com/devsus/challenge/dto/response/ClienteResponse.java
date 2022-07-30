package com.devsus.challenge.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClienteResponse {

    private Long clienteId;

    private String name;

    private String gender;

    private Long age;

    private String address;

    private String phone;

    private Boolean status;

    private Long dni;
}
