package com.matheus_eg.help_desk_api.exception;

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
