package com.controller;

import com.database.enums.PrioridadeEnum;
import com.database.enums.StatusEnum;
import com.database.model.ChamadoModel;
import com.dto.ChamadoDTO;
import com.exception.AlreadyExistsException;
import com.exception.CallCompletedException;
import com.exception.CallInactiveException;
import com.exception.NotFoundException;
import com.service.ChamadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/chamado")
@RequiredArgsConstructor
public class ChamadoController {

    private final ChamadoService chamadoService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ChamadoModel> findAll() {
        return chamadoService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ChamadoDTO createChamado(@RequestBody ChamadoDTO chamadoDTO) throws AlreadyExistsException {
        return chamadoService.createdChamado(chamadoDTO);
    }

    @PatchMapping(value = "/{id}/{prioridade}")
    @ResponseStatus(HttpStatus.OK)
    public ChamadoDTO updatePrioridadeChamado(@PathVariable("id") Long id, @PathVariable ("prioridade") PrioridadeEnum prioridade) throws NotFoundException, CallCompletedException, CallInactiveException {
        return chamadoService.updatePrioridadeChamado(id, prioridade);
    }

    @PatchMapping(value = "/{id}/{status}")
    @ResponseStatus(HttpStatus.OK)
    public ChamadoDTO updateStatusChamado(@PathVariable ("id") Long id, @PathVariable ("status") StatusEnum status) throws NotFoundException, CallCompletedException, CallInactiveException {
        return chamadoService.updateStatusChamado(id, status);
    }

    @DeleteMapping(value = "/{id}/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativarChamado(@PathVariable("id") Long id) throws NotFoundException {
        chamadoService.inativarChamado(id);
    }

}
