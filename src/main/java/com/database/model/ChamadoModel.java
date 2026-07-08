package com.database.model;

import com.database.enums.PrioridadeEnum;
import com.database.enums.StatusEnum;
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
