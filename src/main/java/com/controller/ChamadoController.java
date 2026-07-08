package com.controller;

import com.database.enums.PrioridadeEnum;
import com.database.enums.StatusEnum;
import com.database.model.ChamadoModel;
import com.dto.ChamadoDTO;
import com.exception.NotFoundException;
import com.service.ChamadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/contrl")
@RequiredArgsConstructor
public class ChamadoController {

    private final ChamadoService chamadoService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ChamadoModel> finAll() {
        return chamadoService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ChamadoModel createChamado(@RequestBody ChamadoDTO chamadoDTO) {
        return chamadoService.createdChamado(chamadoDTO);
    }

    @PatchMapping(value = "/{id}/prioridade")
    @ResponseStatus(HttpStatus.OK)
    public ChamadoModel updatePrioridadeChamado(@PathVariable("id") Integer id, @RequestParam ("prioridade") PrioridadeEnum prioridade) throws NotFoundException {
        return chamadoService.updatePrioridadeChamado(id, prioridade);
    }

    @PatchMapping(value = "/{id}/status")
    @ResponseStatus(HttpStatus.OK)
    public ChamadoModel updateStatusChamado(@PathVariable ("id") Integer id, @RequestParam ("status") StatusEnum status) throws NotFoundException {
        return chamadoService.updateStatusChamado(id, status);
    }

    @DeleteMapping(value = "/{id}/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteChamado(@PathVariable("id") Integer id) {
        chamadoService.deleteChamado(id);
    }

}
