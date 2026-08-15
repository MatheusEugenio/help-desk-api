package com.service;

import com.database.model.ChamadoModel;
import com.database.enums.PrioridadeEnum;
import com.database.enums.StatusEnum;
import com.database.model.HistoricoChamadoModel;
import com.database.repository.IChamadoRepository;
import com.database.repository.IHistoricoChamadoRepository;
import com.dto.ChamadoDTO;
import com.exception.AlreadyExistsException;
import com.exception.CallCompletedException;
import com.exception.CallInactiveException;
import com.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChamadoService {

    private final IChamadoRepository chamadoRepository;
    private final IHistoricoChamadoRepository historicoRepository;

    public List<ChamadoModel> findAll(){
        return chamadoRepository.findAll();
    }

    @Transactional(rollbackFor = Exception.class)
    public ChamadoDTO createdChamado(ChamadoDTO chamadoDTO) throws AlreadyExistsException {

        ChamadoModel chamado = chamadoRepository.findByTitulo(chamadoDTO.getTitulo())
                .orElse(null);

        if (chamado != null) {
            throw new AlreadyExistsException("Já existe chamado com o mesmo título!");
        }

        chamado = ChamadoModel.builder()
                .titulo(chamadoDTO.getTitulo())
                .descricao(chamadoDTO.getDescricao())
                .status(StatusEnum.ABERTO)
                .prioridade(PrioridadeEnum.BAIXA)
                .solicitante(chamadoDTO.getSolicitante())
                .categoria(chamadoDTO.getCategoria())
                .build();

        chamadoRepository.save(chamado);

        HistoricoChamadoModel historico = HistoricoChamadoModel.builder()
                .tipoAlteracao("CRIAÇÃO")
                .valorAnterior(null)
                .novoValor(StatusEnum.ABERTO.toString())
                .chamado(chamado)
                .autor(chamado.getSolicitante())
                .build();

        chamado.getHistoricos().add(historico);

        historicoRepository.save(historico);

        return convertForChamadoDTO(chamado);
    }

    @Transactional(rollbackFor = Exception.class)
    public ChamadoDTO updatePrioridade(Long id, PrioridadeEnum prioridade) throws NotFoundException, CallCompletedException, CallInactiveException {

        ChamadoModel chamado = chamadoRepository.findById(id)
                .orElse(null);

        if (chamado == null){
            throw new NotFoundException("Chamado com id = "+id+" não encontrado");
        }

        if (chamado.getStatus().equals(StatusEnum.FINALIZADO)) {
            throw new CallCompletedException("Impossível alterar prioridade, chamado já foi finalizado!");
        }

        if (chamado.getStatus().equals(StatusEnum.INATIVO)){
            throw new CallInactiveException("Impossível alterar prioridade, chamado já está inativo!");
        }

        var valorAnterior = chamado.getStatus().toString();

        chamado.setPrioridade(prioridade);

        chamadoRepository.save(chamado);

        HistoricoChamadoModel historico = HistoricoChamadoModel.builder()
                .tipoAlteracao("ATUALIZAÇÃO NA PRIORIADADE")
                .valorAnterior(valorAnterior)
                .novoValor(chamado.getPrioridade().toString())
                .chamado(chamado)
                .autor(chamado.getSolicitante())
                .build();

        chamado.getHistoricos().add(historico);

        historicoRepository.save(historico);

        return convertForChamadoDTO(chamado);
    }

    @Transactional(rollbackFor = Exception.class)
    public ChamadoDTO updateStatus(Long id, StatusEnum status) throws NotFoundException, CallCompletedException, CallInactiveException {

        ChamadoModel chamado = chamadoRepository.findById(id)
                .orElse(null);

        if (chamado == null){
            throw new NotFoundException("Chamado com id = "+id+" não encontrado");
        }

        if (chamado.getStatus().equals(StatusEnum.FINALIZADO)) {
            throw new CallCompletedException("Impossível alterar status, chamado já foi finalizado!");
        }

        if (chamado.getStatus().equals(StatusEnum.INATIVO)){
            throw new CallInactiveException("Impossível alterar status, chamado já está inativo!");
        }

        var valorAnterior = chamado.getStatus().toString();

        chamado.setStatus(status);

        chamadoRepository.save(chamado);

        HistoricoChamadoModel historico = HistoricoChamadoModel.builder()
                .tipoAlteracao("ATUALIZAÇÃO NA PRIORIADADE")
                .valorAnterior(valorAnterior)
                .novoValor(chamado.getStatus().toString())
                .chamado(chamado)
                .autor(chamado.getSolicitante())
                .build();

        chamado.getHistoricos().add(historico);

        historicoRepository.save(historico);

        return convertForChamadoDTO(chamado);
    }

    @Transactional(rollbackFor = Exception.class)
    public void inativarChamado(Long id) throws NotFoundException {

        ChamadoModel chamado = chamadoRepository.findById(id)
                .orElse(null);

        if (chamado == null) {
            throw new NotFoundException("Chamado com id = " + id + " não encontrado");
        }

        var valorAnterior = chamado.getStatus().toString();

        chamado.setStatus(StatusEnum.INATIVO);

        chamadoRepository.save(chamado);

        HistoricoChamadoModel historico = HistoricoChamadoModel.builder()
                .tipoAlteracao("INATIVAÇÃO")
                .valorAnterior(valorAnterior)
                .novoValor(chamado.getStatus().toString())
                .chamado(chamado)
                .autor(chamado.getSolicitante())
                .build();

        chamado.getHistoricos().add(historico);

        historicoRepository.save(historico);
    }

    /////////////////////////////////////
    /// PRIVATE METHODS
    /////////////////////////////////////

    private ChamadoDTO convertForChamadoDTO(ChamadoModel chamado) {
        return ChamadoDTO.builder()
                .titulo(chamado.getTitulo())
                .status(chamado.getStatus())
                .prioridade(chamado.getPrioridade())
                .solicitante(chamado.getSolicitante())
                .build();
    }
}
