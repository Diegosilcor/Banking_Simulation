package com.devsus.challenge.entity;

import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "clientes")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@SQLDelete(sql = "UPDATE clientes SET soft_delete = true WHERE cliente_id=?")
@Where(clause = "soft_delete = false")
public class ClienteEntity extends PersonaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @NotEmpty
    @NotBlank
    @Column(name ="cliente_id")
    private Long clienteId;

    private String password;

    private Boolean status;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private Set<CuentaEntity> cuentas;

    @Column(name = "soft_delete")
    private Boolean softDelete = Boolean.FALSE;

}
