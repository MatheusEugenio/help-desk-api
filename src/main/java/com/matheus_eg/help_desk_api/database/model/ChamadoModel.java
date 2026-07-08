package com.matheus_eg.help_desk_api.database.model;

import com.matheus_eg.help_desk_api.database.enums.PrioridadeEnum;
import com.matheus_eg.help_desk_api.database.enums.StatusEnum;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChamadoModel {

    private Integer id;
    private String titulo;
    private StatusEnum status;
    private PrioridadeEnum prioridade;
    private SolicitanteModel solicitante;
}
