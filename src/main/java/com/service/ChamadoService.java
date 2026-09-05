package com.service;

import com.database.enums.PapelUsuarioEnum;
import com.database.enums.PrioridadeEnum;
import com.database.enums.StatusEnum;
import com.database.model.Categoria;
import com.database.model.ChamadoModel;
import com.database.model.HistoricoChamadoModel;
import com.database.model.UsuarioModel;
import com.database.repository.ICategoriaRepository;
import com.database.repository.IChamadoRepository;
import com.database.repository.IHistoricoChamadoRepository;
import com.database.repository.IUsuarioRepository;
import com.database.specifications.ChamadoSpecification;
import com.dto.ChamadoRequiredDTO;
import com.dto.ResponseChamadoDTO;
import com.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                .map(this::mapToResponseChamado)
                .toList();
    }

    public List<HistoricoChamadoModel> historicoChamado(Long id) throws NotFoundException {
        ChamadoModel chamado = chamadoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Chamado com id = " + id + " não encontrado"));

        return chamado.getHistorico();
    }

    public ResponseChamadoDTO findByID(Long id) throws NotFoundException {
        ChamadoModel chamado = chamadoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Chamado com id = " + id + " não encontrado"));

        return mapToResponseChamado(chamado);
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseChamadoDTO createdChamado(ChamadoRequiredDTO chamadoRequiredDTO) throws AlreadyExistsException, NotFoundException, InappropriateUserRoleException {

        chamadoRepository.findByTitulo(chamadoRequiredDTO.getTitulo())
                .orElseThrow(() -> new AlreadyExistsException("Já existe chamado com o mesmo título!"));
        ChamadoModel chamado;

        UsuarioModel solicitante = usuarioRepository.findById(chamadoRequiredDTO.getIdSolicitante())
                .orElseThrow(() -> new NotFoundException("Usuario com ID = " + chamadoRequiredDTO.getIdSolicitante() + " não encontrado"));

        if (!solicitante.getPapel().equals(PapelUsuarioEnum.COLABORADOR)) {
            throw new InappropriateUserRoleException("O usuário não é colaborador");
        }

        UsuarioModel atendente = usuarioRepository.findById(chamadoRequiredDTO.getIdAtendente())
                .orElseThrow(() -> new NotFoundException("Usuario com ID = " + chamadoRequiredDTO.getIdSolicitante() + " não encontrado"));

        if (!atendente.getPapel().equals(PapelUsuarioEnum.ATENDENTE)) {
            throw new InappropriateUserRoleException("O usuário não é atendente");
        }

        Categoria categoria = categoriaRepository.findById(chamadoRequiredDTO.getIdCategoria())
                .orElseThrow(() -> new NotFoundException("Categoria com ID = " + chamadoRequiredDTO.getIdCategoria() + " não encontrada"));

        chamado = ChamadoModel.builder()
                .titulo(chamadoRequiredDTO.getTitulo())
                .descricao(chamadoRequiredDTO.getDescricao())
                .status(StatusEnum.ABERTO)
                .prioridade(PrioridadeEnum.BAIXA)
                .solicitante(solicitante)
                .categoria(categoria)
                .atendente(atendente)
                .build();

        chamadoRepository.save(chamado);

        persistInHistoricoChamado(null, "CRIAÇÃO", chamado.getStatus().toString(), chamado);

        return mapToResponseChamado(chamado);
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseChamadoDTO assignAtendente(Long idChamado, Long idAtendente) throws NotFoundException, InappropriateUserRoleException {

        UsuarioModel atendente = usuarioRepository.findById(idAtendente)
                .orElseThrow(() -> new NotFoundException("Este atendente não existe"));

        if (!atendente.getPapel().equals(PapelUsuarioEnum.ATENDENTE)) {
            throw new InappropriateUserRoleException("O usuário não é um atendente");
        }

        ChamadoModel chamado = chamadoRepository.findById(idChamado)
                .orElseThrow(() -> new NotFoundException("O chamado não existe "));

        var atendenteAnterior = chamado.getAtendente();

        chamado.setAtendente(atendente);

        chamadoRepository.save(chamado);

        persistInHistoricoChamado(atendenteAnterior.getNome(), "Atribuição de Atendente", chamado.getAtendente().getNome(), chamado);

        return mapToResponseChamado(chamado);
    }

    public ResponseChamadoDTO finishChamado(Long idChamado) throws NotFoundException, CallCompletedException {

        ChamadoModel chamado = chamadoRepository.findById(idChamado)
                .orElseThrow(() -> new NotFoundException("Chamado com id = " + idChamado + " não encontrado"));

        if (chamado.getStatus().equals(StatusEnum.FINALIZADO)) {
            throw new CallCompletedException("O chamado já foi finalizado");
        }

        var valorAnterior = chamado.getStatus();

        chamado.setStatus(StatusEnum.FINALIZADO);

        chamadoRepository.save(chamado);

        persistInHistoricoChamado(valorAnterior.toString(), "CHAMADO FINALIZADO", chamado.getStatus().toString(), chamado);

        return mapToResponseChamado(chamado);
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseChamadoDTO updatePrioridade(Long id, PrioridadeEnum prioridade) throws NotFoundException, CallCompletedException {

        ChamadoModel chamado = chamadoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Chamado com id = " + id + " não encontrado"));

        if (chamado.getStatus().equals(StatusEnum.FINALIZADO)) {
            throw new CallCompletedException("Impossível alterar prioridade, chamado já foi finalizado!");
        }

        var valorAnterior = chamado.getPrioridade().toString();

        chamado.setPrioridade(prioridade);

        chamadoRepository.save(chamado);

        persistInHistoricoChamado(valorAnterior, "ATUALIZAÇÃO NA PRIORIADADE", chamado.getPrioridade().toString(), chamado);

        return mapToResponseChamado(chamado);
    }

    public ResponseChamadoDTO reopenChamado(Long idChamado) throws NotFoundException, CallNotCompletedException {

        ChamadoModel chamado = chamadoRepository.findById(idChamado)
                .orElseThrow(() -> new NotFoundException("Chamado com id = " + idChamado + " não encontrado"));

        if (!chamado.getStatus().equals(StatusEnum.FINALIZADO)){
            throw new CallNotCompletedException("O chamado precisa estar obrigatoriamente finalizado para a reabertura");
        }

        var statusAnterior = chamado.getStatus();

        chamado.setStatus(StatusEnum.ABERTO);

        chamadoRepository.save(chamado);

        persistInHistoricoChamado(statusAnterior.toString(), "REABERTURA DO CHAMADO", chamado.getStatus().toString(), chamado);

        return mapToResponseChamado(chamado);
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseChamadoDTO updateStatus(Long id, StatusEnum status) throws NotFoundException, CallCompletedException {

        ChamadoModel chamado = chamadoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Chamado com id = " + id + " não encontrado"));

        if (chamado.getStatus().equals(StatusEnum.FINALIZADO)) {
            throw new CallCompletedException("Impossível alterar status, chamado já foi finalizado!");
        }

        var valorAnterior = chamado.getStatus().toString();

        chamado.setStatus(status);

        chamadoRepository.save(chamado);

        persistInHistoricoChamado(valorAnterior, "ATUALIZAÇÃO NO STATUS", chamado.getStatus().toString(), chamado);

        return mapToResponseChamado(chamado);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteChamado(Long id) throws NotFoundException {

        ChamadoModel chamado = chamadoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Chamado com id = " + id + " não encontrado"));

        var valorAnterior = chamado.getStatus().toString();

        persistInHistoricoChamado(valorAnterior, "INATIVAÇÃO DO CHAMADO", "CHAMADO EXCLUIDO", chamado);

        chamadoRepository.delete(chamado);
    }

    /////////////////////////////////////
    /// PRIVATE METHODS
    /////////////////////////////////////

    private ResponseChamadoDTO mapToResponseChamado(ChamadoModel chamado) {
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
                .autor(chamado.getSolicitante())
                .build();

        chamado.getHistorico().add(historico);

        historicoRepository.save(historico);
    }
}
