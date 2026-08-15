package com.dto;

import com.database.enums.PapelUsuarioEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UsuarioDTO {

    @NotBlank
    private String nome;

    @NotBlank
    @Email(message = "formato de email inválido")
    private String email;

    @NotNull
    private PapelUsuarioEnum papel;
}
