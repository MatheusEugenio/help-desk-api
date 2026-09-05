package com.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ResponseComentarioDTO {

    @NotBlank
    private String mensagem;

    @NotNull
    private String tituloChamado;

    @NotNull
    private String emailUsuario;

}
