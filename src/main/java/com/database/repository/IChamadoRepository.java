package com.database.repository;

import com.database.model.ChamadoModel;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IChamadoRepository extends JpaRepository<ChamadoModel, Long>, Specification<ChamadoModel> {

    Optional<ChamadoModel> findByTitulo(String titulo);

}
