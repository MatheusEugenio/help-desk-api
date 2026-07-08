package com.matheus_eg.help_desk_api.dto;

import com.matheus_eg.help_desk_api.database.enums.PrioridadeEnum;
import com.matheus_eg.help_desk_api.database.model.SolicitanteModel;
import com.matheus_eg.help_desk_api.database.enums.StatusEnum;
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
