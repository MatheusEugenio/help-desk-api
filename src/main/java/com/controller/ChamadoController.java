package com.controller;

import com.database.enums.PrioridadeEnum;
import com.database.enums.StatusEnum;
import com.database.model.ChamadoModel;
import com.dto.ChamadoDTO;
import com.dto.ResponseChamadoDTO;
import com.exception.AlreadyExistsException;
import com.exception.CallCompletedException;
import com.exception.CallInactiveException;
import com.exception.NotFoundException;
import com.service.ChamadoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/chamado")
@RequiredArgsConstructor
@Validated
public class ChamadoController {

    private final ChamadoService chamadoService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ChamadoModel> findAll() {
        return chamadoService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseChamadoDTO createChamado(@Valid @RequestBody ChamadoDTO chamadoDTO) throws AlreadyExistsException, NotFoundException {
        return chamadoService.createdChamado(chamadoDTO);
    }

    @PatchMapping(value = "/{id}/{prioridade}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseChamadoDTO updatePrioridadeChamado(@Valid @PathVariable("id") Long id, @Valid @PathVariable ("prioridade") PrioridadeEnum prioridade) throws NotFoundException, CallCompletedException, CallInactiveException {
        return chamadoService.updatePrioridade(id, prioridade);
    }

    @PatchMapping(value = "/{id}/{status}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseChamadoDTO updateStatusChamado(@Valid @PathVariable ("id") Long id, @Valid @PathVariable ("status") StatusEnum status) throws NotFoundException, CallCompletedException, CallInactiveException {
        return chamadoService.updateStatus(id, status);
    }

    @DeleteMapping(value = "/{id}/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativarChamado(@Valid @PathVariable("id") Long id) throws NotFoundException {
        chamadoService.inativarChamado(id);
    }

}
