package com.matheus_eg.help_desk_api.database.model;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SolicitanteModel {

    private String nome;
    private String email;
}
