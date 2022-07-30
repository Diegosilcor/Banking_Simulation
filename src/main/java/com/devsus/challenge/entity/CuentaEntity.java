package com.devsus.challenge.entity;

import com.devsus.challenge.utility.CuentaTypeEnum;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "cuentas")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@SQLDelete(sql = "UPDATE cuentas SET soft_delete = true WHERE cuenta_id=?")
@Where(clause = "soft_delete = false")
public class CuentaEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @NotEmpty
    @NotBlank
    @Column(name = "cuenta_id")
    private Long cuentaId;

    private CuentaTypeEnum type;

    private Long balance;

    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private ClienteEntity cliente;

    @OneToMany(mappedBy = "cuenta", cascade = CascadeType.ALL)
    private Set<MovimientoEntity> movimientos;

    @Column(name = "soft_delete")
    private Boolean softDelete = Boolean.FALSE;
}
