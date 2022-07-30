package com.devsus.challenge.dto.response;

import com.devsus.challenge.utility.CuentaTypeEnum;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

@Data
@Builder
public class CuentaResponse {

    private Long cuentaId;

    private CuentaTypeEnum type;

    private Long balance;

    private Boolean status;
}
