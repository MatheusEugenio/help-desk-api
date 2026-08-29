package com.service;

import com.database.model.Categoria;
import com.database.model.ChamadoModel;
import com.database.enums.PrioridadeEnum;
import com.database.enums.StatusEnum;
import com.database.model.HistoricoChamadoModel;
import com.database.model.UsuarioModel;
import com.database.repository.ICategoriaRepository;
import com.database.repository.IChamadoRepository;
import com.database.repository.IHistoricoChamadoRepository;
import com.database.repository.IUsuarioRepository;
import com.database.specifications.ChamadoSpecification;
import com.dto.ChamadoDTO;
import com.dto.ResponseChamadoDTO;
import com.exception.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChamadoService {

    private final IChamadoRepository chamadoRepository;
    private final IHistoricoChamadoRepository historicoRepository;
    private final IUsuarioRepository usuarioRepository;
    private final ICategoriaRepository categoriaRepository;

    public List<ResponseChamadoDTO> findAll(StatusEnum status,
                                            PrioridadeEnum prioridade,
                                            Long idCategoria,
                                            Long idSolicitante){

        Specification<ChamadoModel> filtro = Specification
                .where(ChamadoSpecification.byStatus(status))
                .and(ChamadoSpecification.byPrioridade(prioridade))
                .and(ChamadoSpecification.byCategoria(idCategoria))
                .and(ChamadoSpecification.bySolicitante(idSolicitante));

        return chamadoRepository.findAll(filtro).stream()
                .map(this::convertForResponseChamado)
                .toList();
    }

    public List<HistoricoChamadoModel> historicoChamadoFindByID(Long id) throws NotFoundException {
        ChamadoModel chamado = chamadoRepository.findById(id)
                .orElse(null);

        if (chamado == null){
            throw new NotFoundException("Chamado com id = "+id+" não encontrado");
        }

        return chamado.getHistorico();
    }

    public ResponseChamadoDTO findByID(@Valid @PathVariable Long id) throws NotFoundException {
        ChamadoModel chamado = chamadoRepository.findById(id)
                .orElse(null);

        if (chamado == null){
            throw new NotFoundException("Chamado com id = "+id+" não encontrado");
        }

        return convertForChamadoDTO(chamado);
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseChamadoDTO createdChamado(ChamadoDTO chamadoDTO) throws AlreadyExistsException, NotFoundException {

        ChamadoModel chamado = chamadoRepository.findByTitulo(chamadoDTO.getTitulo())
                .orElse(null);

        if (chamado != null) {
            throw new AlreadyExistsException("Já existe chamado com o mesmo título!");
        }

        UsuarioModel solicitante = usuarioRepository.findById(chamadoDTO.getIdSolicitante())
                .orElse(null);

        if (solicitante == null) {
            throw new NotFoundException("Usuario com ID = "+chamadoDTO.getIdSolicitante()+" não encontrado");
        }

        Categoria categoria = categoriaRepository.findById(chamadoDTO.getIdCategoria())
                .orElse(null);

        if (categoria == null) {
            throw new NotFoundException("Categoria com ID = "+chamadoDTO.getIdCategoria()+" não encontrada");
        }

        chamado = ChamadoModel.builder()
                .titulo(chamadoDTO.getTitulo())
                .descricao(chamadoDTO.getDescricao())
                .status(StatusEnum.ABERTO)
                .prioridade(PrioridadeEnum.BAIXA)
                .solicitante(solicitante)
                .categoria(categoria)
                .build();

        chamadoRepository.save(chamado);

        HistoricoChamadoModel historico = HistoricoChamadoModel.builder()
                .tipoAlteracao("CRIAÇÃO")
                .valorAnterior(null)
                .novoValor(StatusEnum.ABERTO.toString())
                .chamado(chamado)
                .autor(chamado.getSolicitante())
                .build();

        chamado.getHistorico().add(historico);

        historicoRepository.save(historico);

        return convertForChamadoDTO(chamado);
    }

    @Transactional(rollbackFor = Exception.class)
    public void assignAtendente(Long idChamado, Long idAtendente) throws NotFoundException, CallInactiveException {

        UsuarioModel atendente = usuarioRepository.findById(idAtendente)
                .orElse(null);

        if (atendente == null) {
            throw new NotFoundException("Este atendente não existe");
        }

        // LÓGICA DE VERIFICAÇÃO DE ATENDENTE ATIVO

        ChamadoModel chamado = chamadoRepository.findById(idChamado)
                .orElse(null);

        if (chamado == null) {
            throw new NotFoundException("O chamado não existe ");
        }

        if (chamado.getStatus().equals(StatusEnum.INATIVO)) {
            throw new CallInactiveException("Chamado inativo");
        }

        var atendenteAnterior = chamado.getAtendente();

        chamado.setAtendente(atendente);

        chamadoRepository.save(chamado);

        HistoricoChamadoModel historico = HistoricoChamadoModel.builder()
                .tipoAlteracao("Atribuição de Atendente")
                .valorAnterior(atendenteAnterior.getNome())
                .novoValor(chamado.getAtendente().getNome())
                .chamado(chamado)
                .autor(chamado.getSolicitante())
                .build();

        chamado.getHistorico().add(historico);

        historicoRepository.save(historico);
    }

    public void fecharChamado(){}

    @Transactional(rollbackFor = Exception.class)
    public ResponseChamadoDTO updatePrioridade(Long id, PrioridadeEnum prioridade) throws NotFoundException, CallCompletedException, CallInactiveException {

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

        chamado.getHistorico().add(historico);

        historicoRepository.save(historico);

        return convertForChamadoDTO(chamado);
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseChamadoDTO updateStatus(Long id, StatusEnum status) throws NotFoundException, CallCompletedException, CallInactiveException {

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

        chamado.getHistorico().add(historico);

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

        chamado.getHistorico().add(historico);

        historicoRepository.save(historico);
    }

    /////////////////////////////////////
    /// PRIVATE METHODS
    /////////////////////////////////////

    private ResponseChamadoDTO convertForResponseChamado(ChamadoModel chamado) {
        return ResponseChamadoDTO.builder()
                .titulo(chamado.getTitulo())
                .descricao(chamado.getDescricao())
                .prioridade(chamado.getPrioridade())
                .status(chamado.getStatus())
                .nomeCategoria(chamado.getNomeCategoria())
                .nomeSolicitante(chamado.getNomeSolicitane())
                .build();
    }

    private void persistInHistoricoChamado(String valorAnterior, String tipoAlteracao, String novoValor, ChamadoModel chamado) {

        HistoricoChamadoModel historico = HistoricoChamadoModel.builder()
                .tipoAlteracao(tipoAlteracao)
                .valorAnterior(valorAnterior)
                .novoValor(novoValor)
                .chamado(chamado)
                .autor(chamado.getAtendente())
                .build();

        chamado.getHistorico().add(historico);

        historicoRepository.save(historico);
    }
}
