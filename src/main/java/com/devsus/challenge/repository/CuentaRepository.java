package com.devsus.challenge.repository;

import com.devsus.challenge.entity.CuentaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CuentaRepository extends JpaRepository<CuentaEntity, Long> {
}
