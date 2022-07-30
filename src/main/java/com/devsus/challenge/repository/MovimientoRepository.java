package com.devsus.challenge.repository;

import com.devsus.challenge.entity.MovimientoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.Calendar;
import java.util.Set;

@Repository
public interface MovimientoRepository extends JpaRepository<MovimientoEntity, Long> {
}
