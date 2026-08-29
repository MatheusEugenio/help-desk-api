package com.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseCategoriaDTO {

    @NotBlank
    private String nomeCategoria;
}
