package com.database.repository;

import com.database.model.ChamadoModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IChamadoRepository extends JpaRepository<ChamadoModel, Long> {
}
