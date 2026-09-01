package com.database.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum PapelUsuarioEnum {
    COLABORADOR("colaborador"),
    ATENDENTE("atendente"),
    ADMINISTRADOR("administrador");

    @JsonValue
    private final String descricao;

    PapelUsuarioEnum(String descricao) {
        this.descricao = descricao;
    }

}
