package com.database.specifications;

import com.database.enums.PrioridadeEnum;
import com.database.enums.StatusEnum;
import com.database.model.ChamadoModel;
import org.springframework.data.jpa.domain.Specification;
public class ChamadoSpecification {

    public static Specification<ChamadoModel> byStatus(StatusEnum statusParam){
        return (root, query, cb) -> {
            if (statusParam == null){return null;}

            return cb.equal(root.get("status"), statusParam);
        };
    }

    public static Specification<ChamadoModel> byPrioridade(PrioridadeEnum prioridadeParam){
        return (root, query, cb) -> {
            if (prioridadeParam == null){return null;}

            return cb.equal(root.get("prioridade"), prioridadeParam);
        };
    }

    public static Specification<ChamadoModel> byCategoria(Long idCategoria){
        return (root, query, cb) -> {
            if (idCategoria == null){return null;}

            return cb.equal(root.get("categoria").get("id"), idCategoria);
        };
    }

    public static Specification<ChamadoModel> bySolicitante(Long idSolicitante){
        return (root, query, cb) -> {
            if (idSolicitante == null){return null;}

            return cb.equal(root.get("solicitante").get("id"), idSolicitante);
        };
    }

    public static Specification<ChamadoModel> byTitulo(String titulo) {
        return (root, query, cb) -> {
            if (titulo == null) {
                return null;
            }

            return cb.equal(cb.lower(root.get("titulo")), titulo.toLowerCase());
        };
    }

    public static Specification<ChamadoModel> byId(Long idChamado) {
        return (root, query, cb) -> {
            if (idChamado == null) {
                return null;
            }

            return cb.equal(root.get("id"), idChamado);
        };
    }

}
