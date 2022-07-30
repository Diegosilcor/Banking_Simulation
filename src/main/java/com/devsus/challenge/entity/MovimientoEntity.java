package com.devsus.challenge.entity;

import com.devsus.challenge.utility.MovimientoTypeEnum;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Calendar;

@Entity
@Table(name = "movimientos")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@SQLDelete(sql = "UPDATE movimientos SET soft_delete = true WHERE movimiento_id=?")
@Where(clause = "soft_delete = false")
public class MovimientoEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movimiento_id")
    @NotNull
    @NotEmpty
    @NotBlank
    private Long movimientoId;

    private Timestamp date;

    private MovimientoTypeEnum type;

    private Long amount;

    private Long final_balance;

    @ManyToOne
    @JoinColumn(name = "cuenta_id")
    private CuentaEntity cuenta;

    @Column(name = "soft_delete")
    private Boolean softDelete = Boolean.FALSE;

}
