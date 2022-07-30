package com.devsus.challenge.dto.request;

import com.devsus.challenge.utility.MovimientoTypeEnum;
import lombok.Data;
import lombok.Getter;

import java.sql.Date;
import java.util.Calendar;

@Data
public class MovimientoRequest {

    private MovimientoTypeEnum type;

    private Long amount;
}
