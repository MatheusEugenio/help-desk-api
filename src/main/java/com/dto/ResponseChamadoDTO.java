package com.dto;

import com.database.enums.PrioridadeEnum;
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
public class ResponseChamadoDTO {

    @NotBlank
    private String titulo;

    @NotBlank
    private String descricao;

    @NotNull
    private PrioridadeEnum prioridade;

    @NotNull
    private StatusEnum status;

    @NotNull
    private String nomeCategoria;

    @NotNull
    private String nomeSolicitante;
}
