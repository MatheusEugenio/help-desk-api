package com.database.repository;

import com.database.model.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface IUsuarioRepository extends JpaRepository<UsuarioModel, Long>, JpaSpecificationExecutor<UsuarioModel> {

    Optional<UsuarioModel> findByEmail(String email);
}
