package com.database.repository;

import com.database.model.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsuarioRepository extends JpaRepository<UsuarioModel, Long> {
}
