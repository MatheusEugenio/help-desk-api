package com.database.repository;

import com.database.model.ComentarioModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IComentarioRepository extends JpaRepository<ComentarioModel, Integer> {
}
