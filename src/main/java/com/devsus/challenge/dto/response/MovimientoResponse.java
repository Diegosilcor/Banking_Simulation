package com.devsus.challenge.dto.response;

import com.devsus.challenge.utility.MovimientoTypeEnum;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;

@Builder
@Data
public class MovimientoResponse {

    private Long movimientoId;

    private Timestamp date;

    private MovimientoTypeEnum type;

    private Long amount;

    private Long final_balance;

}
