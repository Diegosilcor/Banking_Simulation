package com.devsus.challenge.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class ClienteRequest {

    private String name;

    private String gender;

    private String password;

    private Long age;

    private String address;

    private String phone;

    private Boolean status;

    private Long dni;

}
