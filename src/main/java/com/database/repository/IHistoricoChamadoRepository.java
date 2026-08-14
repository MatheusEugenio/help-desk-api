package com.database.repository;

import com.database.model.HistoricoChamadoModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IHistoricoChamadoRepository extends JpaRepository<HistoricoChamadoModel, Long> {
}
