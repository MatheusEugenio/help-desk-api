package com.dto;

import com.database.enums.PrioridadeEnum;
import com.database.model.SolicitanteModel;
import com.database.enums.StatusEnum;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChamadoDTO {

    private String titulo;
    private StatusEnum status;
    private PrioridadeEnum prioridade;
    private SolicitanteModel solicitante;
}
