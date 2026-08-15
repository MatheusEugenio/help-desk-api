package com.dto;

import com.database.enums.PrioridadeEnum;
import com.database.model.Categoria;
import com.database.model.UsuarioModel;
import com.database.enums.StatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChamadoDTO {

    @NotBlank
    private String titulo;

    @NotBlank
    private String descricao;

    private PrioridadeEnum prioridade;

    @NotNull
    private StatusEnum status;

    @NotNull
    private Categoria categoria;

    @NotNull
    private UsuarioModel solicitante;
}
