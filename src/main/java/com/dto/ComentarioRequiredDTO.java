package com.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NotNull
@AllArgsConstructor
@NoArgsConstructor
public class ComentarioRequiredDTO {

    @NotBlank
    private String mensagem;

    @NotNull
    private Long idUsuario;

}
