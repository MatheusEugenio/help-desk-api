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
public class ChamadoRequiredDTO {

    @NotBlank
    private String titulo;

    @NotBlank
    private String descricao;

    private PrioridadeEnum prioridade;

    private StatusEnum status;

    @NotNull
    private Long idCategoria;

    @NotNull
    private Long idSolicitante;

    @NotNull
    private Long idAtendente;

}
