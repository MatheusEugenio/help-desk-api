package com.dto;

import com.database.enums.PrioridadeEnum;
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

    @NotNull
    private StatusEnum status;

    @NotNull
    private PrioridadeEnum prioridade;

    @NotNull
    private UsuarioModel solicitante;
}
