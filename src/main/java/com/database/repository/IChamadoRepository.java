package com.database.repository;

import com.database.model.ChamadoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface IChamadoRepository extends JpaRepository<ChamadoModel, Long>, JpaSpecificationExecutor<ChamadoModel> {

    Optional<ChamadoModel> findByTitulo(String titulo);

}
