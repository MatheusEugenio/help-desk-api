package com.database.specifications;

import com.database.enums.PrioridadeEnum;
import com.database.enums.StatusEnum;
import com.database.model.ChamadoModel;
import org.springframework.data.jpa.domain.Specification;

public class ChamadoSpecification {

    public static Specification<ChamadoModel> porStatus(StatusEnum statusParam){
        return (root, query, cb) -> {
            if (statusParam == null){return null;}

            return cb.equal(root.get("status"), statusParam);
        };
    }

    public static Specification<ChamadoModel> porPrioridade(PrioridadeEnum prioridadeParam){
        return (root, query, cb) -> {
            if (prioridadeParam == null){return null;}

            return cb.equal(root.get("prioridade"), prioridadeParam);
        };
    }

    public static Specification<ChamadoModel> porCategoria(Long idCategoria){
        return (root, query, cb) -> {
            if (idCategoria == null){return null;}

            return cb.equal(root.get("categoria").get("id"), idCategoria);
        };
    }

    public static Specification<ChamadoModel> porSolicitante(Long idSolicitante){
        return (root, query, cb) -> {
            if (idSolicitante == null){return null;}

            return cb.equal(root.get("solicitante").get("id"), idSolicitante);
        };
    }

}
