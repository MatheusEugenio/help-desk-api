package com.controller;

import com.database.enums.PapelUsuarioEnum;
import com.database.model.UsuarioModel;
import com.dto.UsuarioDTO;
import com.exception.AlreadyExistsException;
import com.exception.NotFoundException;
import com.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/usuarios")
@Validated
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UsuarioModel> findAll(@RequestParam(name = "letra", required = false) String letraInicial) {
        return usuarioService.findAll(letraInicial);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UsuarioDTO viewUsuarioByID(@Valid @PathVariable Long id) throws NotFoundException {
        return usuarioService.findById(id);
    }

    @GetMapping("/{email}/email")
    @ResponseStatus(HttpStatus.OK)
    public UsuarioDTO viewUsuarioByEmail(@Valid @PathVariable String email) throws NotFoundException {
        return usuarioService.findByEmail(email);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioDTO createdUsuario(@Valid @RequestBody UsuarioDTO usuario) throws AlreadyExistsException {
        return usuarioService.createdUsuario(usuario);
    }

    @DeleteMapping("/{email}/delete")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUsuario(@Valid @PathVariable String email) throws NotFoundException {
        usuarioService.delete(email);
    }

    @PatchMapping("/{id}/{papel}/papel")
    @ResponseStatus(HttpStatus.OK)
    public UsuarioDTO updatePapelUsuario(@Valid @PathVariable Long id,@Valid @PathVariable PapelUsuarioEnum papel) throws NotFoundException {
        return usuarioService.updatePapelUsuario(id, papel);
    }
}
