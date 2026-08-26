package com.database.specifications;

import com.database.model.UsuarioModel;
import org.springframework.data.jpa.domain.Specification;

public class UsuarioSpecification {

    public static Specification<UsuarioModel> byInitialLetter(String letraInicial){
        return ((root, query, cb) -> {
            if (letraInicial == null){return null;}

            return cb.like(root.get("nome"),  letraInicial+"%");
        });
    }
}
