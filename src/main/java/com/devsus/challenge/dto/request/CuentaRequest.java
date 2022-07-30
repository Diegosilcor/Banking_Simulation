package com.devsus.challenge.dto.request;

import com.devsus.challenge.utility.CuentaTypeEnum;
import lombok.Data;
import lombok.Getter;

@Data
public class CuentaRequest {

    private Long id;

    private CuentaTypeEnum type;

    private Long balance;

    private Boolean status;
}
