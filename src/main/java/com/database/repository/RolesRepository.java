package com.database.repository;

import com.database.model.RolesModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepository extends JpaRepository<RolesModel, Long> {
}
