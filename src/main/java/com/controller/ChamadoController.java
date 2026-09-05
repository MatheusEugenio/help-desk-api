package com.controller;

import com.database.enums.PrioridadeEnum;
import com.database.enums.StatusEnum;
import com.database.model.HistoricoChamadoModel;
import com.dto.ChamadoRequiredDTO;
import com.dto.ResponseChamadoDTO;
import com.exception.*;
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
    public List<ResponseChamadoDTO> findAll(@RequestParam(required = false) StatusEnum status,
                                            @RequestParam(required = false) PrioridadeEnum prioridade,
                                            @RequestParam(required = false) Long idCategoria ,
                                            @RequestParam(required = false) Long idSolicitante) {
        return chamadoService.findAll(status, prioridade, idCategoria, idSolicitante);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseChamadoDTO createChamado(@Valid @RequestBody ChamadoRequiredDTO chamadoRequiredDTO) throws AlreadyExistsException, NotFoundException, InappropriateUserRoleException {
        return chamadoService.createdChamado(chamadoRequiredDTO);
    }

    @PatchMapping("/{id_chamado}/atendente/{id_atendente}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseChamadoDTO assignAtendente(@Valid @RequestParam("id_chamado") Long idChamado, @Valid @RequestParam Long id_atendente) throws NotFoundException, InappropriateUserRoleException {
        return chamadoService.assignAtendente(idChamado, id_atendente);
    }

    @GetMapping("/{id_chamado}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseChamadoDTO viewChamadoByID(@Valid @PathVariable("id_chamado") Long idChamado) throws NotFoundException {
        return chamadoService.findByID(idChamado);
    }

    @GetMapping("/{id_chamado}/historico")
    @ResponseStatus(HttpStatus.OK)
    public List<HistoricoChamadoModel> viewHistoricoByIdChamado(@Valid @PathVariable("id_chamado") Long idChamado) throws NotFoundException {
        return chamadoService.historicoChamado(idChamado);
    }

    @PatchMapping("/{id}/reabrir")
    @ResponseStatus(HttpStatus.OK)
    public ResponseChamadoDTO reopenChamado(@Valid @PathVariable Long id) throws CallNotCompletedException, NotFoundException {
        return chamadoService.reopenChamado(id);
    }

    @PatchMapping("/{id_chamado}/{prioridade}/prioridade")
    @ResponseStatus(HttpStatus.OK)
    public ResponseChamadoDTO updatePrioridadeChamado(@Valid @PathVariable("id_chamado") Long idChamado, @Valid @PathVariable PrioridadeEnum prioridade) throws NotFoundException, CallCompletedException {
        return chamadoService.updatePrioridade(idChamado, prioridade);
    }

    @PatchMapping("/{id_chamado}/{status}/status")
    @ResponseStatus(HttpStatus.OK)
    public ResponseChamadoDTO updateStatusChamado(@Valid @PathVariable("id_chamado") Long idChamado, @Valid @PathVariable StatusEnum status) throws NotFoundException, CallCompletedException {
        return chamadoService.updateStatus(idChamado, status);
    }

    @DeleteMapping("/{id}/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteChamado(@Valid @PathVariable Long id) throws NotFoundException {
        chamadoService.deleteChamado(id);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseChamadoDTO finishChamado(@Valid @PathVariable Long id) throws NotFoundException, CallCompletedException {
        return chamadoService.finishChamado(id);
    }

}
