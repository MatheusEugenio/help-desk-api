package com.controller;

import com.dto.ComentarioRequiredDTO;
import com.dto.ResponseComentarioDTO;
import com.exception.NotFoundException;
import com.service.ComentarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/comentarios")
@RequiredArgsConstructor
@Validated
class ComentarioController {

    private final ComentarioService comentarioService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ResponseComentarioDTO> getAllComentarios() {
        return comentarioService.findAll();
    }

    @PostMapping("/{chamadoId}/chamado")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseComentarioDTO createComentario(@Valid @PathVariable Long chamadoId, @Valid @RequestBody ComentarioRequiredDTO comentario) throws NotFoundException {
        return comentarioService.create(chamadoId, comentario);
    }

    @PatchMapping("/{idComentario}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseComentarioDTO updateMensagemComentario(@Valid @PathVariable Long idComentario, @Valid @RequestBody String novaMensagem) throws NotFoundException {
        return comentarioService.updateMensagem(idComentario, novaMensagem);
    }

    @DeleteMapping("/{idComentario}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteComentario(@PathVariable Long idComentario) throws NotFoundException {
        comentarioService.delete(idComentario);
    }

}
