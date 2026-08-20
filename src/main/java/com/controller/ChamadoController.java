package com.controller;

import com.database.enums.PrioridadeEnum;
import com.database.enums.StatusEnum;
import com.database.model.ChamadoModel;
import com.database.model.HistoricoChamadoModel;
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
@RequestMapping("/v1/chamados")
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

    @GetMapping("/id-chamado/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseChamadoDTO viewChamadoByID(@Valid @PathVariable Long id) throws NotFoundException {
        return chamadoService.findByID(id);
    }

    @PutMapping("/historico/id-chamado/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<HistoricoChamadoModel> historicoChamadoFindById(@Valid @PathVariable Long id) throws NotFoundException {
        return chamadoService.historicoChamadoFindByID(id);
    }

    @PatchMapping(value = "/id-chamado/{id}/nova-prioridade/{prioridade}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseChamadoDTO updatePrioridadeChamado(@Valid @PathVariable Long id, @Valid @PathVariable PrioridadeEnum prioridade) throws NotFoundException, CallCompletedException, CallInactiveException {
        return chamadoService.updatePrioridade(id, prioridade);
    }

    @PatchMapping(value = "/id-chamado/{id}/novo-status/{status}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseChamadoDTO updateStatusChamado(@Valid @PathVariable Long id, @Valid @PathVariable StatusEnum status) throws NotFoundException, CallCompletedException, CallInactiveException {
        return chamadoService.updateStatus(id, status);
    }

    @DeleteMapping(value = "/delete/id-chamado/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativarChamado(@Valid @PathVariable Long id) throws NotFoundException {
        chamadoService.inativarChamado(id);
    }

}
