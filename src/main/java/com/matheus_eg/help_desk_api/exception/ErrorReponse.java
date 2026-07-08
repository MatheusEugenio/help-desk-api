package com.matheus_eg.help_desk_api.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorReponse {

    private String mensagem;
    private Integer status;
}
