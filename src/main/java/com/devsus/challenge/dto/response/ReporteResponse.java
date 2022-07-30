package com.devsus.challenge.dto.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ReporteResponse {

    private Long cuenta_id;
    private Long balance;
    private MovimientoResponse movimiento;
}
