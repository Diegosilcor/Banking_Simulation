package com.devsus.challenge.entity;

import lombok.*;

import javax.persistence.*;

@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public abstract class PersonaEntity {

    private Long dni;

    private String name;

    private String gender;

    private Long age;

    private String address;

    private String phone;
}
