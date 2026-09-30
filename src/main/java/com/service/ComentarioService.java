package com.service;

import com.database.model.ChamadoModel;
import com.database.model.ComentarioModel;
import com.database.model.UsuarioModel;
import com.database.repository.ChamadoRepository;
import com.database.repository.ComentarioRepository;
import com.database.repository.UsuarioRepository;
import com.dto.ComentarioRequiredDTO;
import com.dto.ResponseComentarioDTO;
import com.exception.AlreadyExistsException;
import com.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ComentarioService {

    private final ComentarioRepository comentarioRepository;
    private final ChamadoRepository chamadoRepository;
    private final UsuarioRepository usuarioRepository;

    public List<ResponseComentarioDTO> findAll() {
        return comentarioRepository.findAll()
                .stream()
                .map(this::mapToResponseComentarioDTO)
                .toList();
    }

    public ResponseComentarioDTO create(Long idChamado, ComentarioRequiredDTO comentarioRequiredDTO) throws NotFoundException, AlreadyExistsException {

        UsuarioModel userRemetente = usuarioRepository.findById(comentarioRequiredDTO.getIdUsuarioRemetente())
                .orElseThrow(() -> new NotFoundException("Remetente não encontrado"));

        UsuarioModel userDestinatario = usuarioRepository.findById(comentarioRequiredDTO.getIdUsuarioDestinatario())
                .orElseThrow(() -> new NotFoundException("Destinatário não encontrado"));

        if (userRemetente.getId().equals(userDestinatario.getId())) {
            throw new AlreadyExistsException("Não é possível enviar um comentário para o mesmo usuário");
        }

        ChamadoModel chamado = chamadoRepository.findById(idChamado)
                .orElseThrow(() -> new NotFoundException("Chamado não encontrado"));

        if (comentarioRequiredDTO.getMensagem().isBlank()) {
            throw new IllegalArgumentException("A mensagem do comentário não pode ser nula ou vazia");
        }

        ComentarioModel comentario = ComentarioModel.builder()
                .chamado(chamado)
                .usuarioRemetente(userRemetente)
                .usuarioDestinatario(userDestinatario)
                .mensagem(comentarioRequiredDTO.getMensagem())
                .build();

        comentarioRepository.save(comentario);

        return mapToResponseComentarioDTO(comentario);
    }

    public ResponseComentarioDTO updateMensagem(Long idComentario, String novaMensagem) throws NotFoundException {
        ComentarioModel comentario = comentarioRepository.findById(idComentario)
                .orElseThrow(() -> new NotFoundException("Comentário não encontrado"));

        if (novaMensagem.isBlank()) {
            throw new IllegalArgumentException("A mensagem do comentário não pode ser nula ou vazia");
        }

        comentario.setMensagem(novaMensagem);
        comentarioRepository.save(comentario);

        return mapToResponseComentarioDTO(comentario);
    }

    public void delete(Long idComentario) throws NotFoundException {
        ComentarioModel comentario = comentarioRepository.findById(idComentario)
                .orElseThrow(() -> new NotFoundException("Comentário não encontrado"));

        comentarioRepository.delete(comentario);
    }

    public void deleteAll() {
        comentarioRepository.deleteAll();
    }

    /// //////////////////////////////////
    /// PRIVATE METHODS
    /// //////////////////////////////////

    private ResponseComentarioDTO mapToResponseComentarioDTO(ComentarioModel comentario) {
        return ResponseComentarioDTO.builder()
                .emailUsuarioRemetente(comentario.getUsuarioRemetente().getEmail())
                .emailUsuarioDestinatario(comentario.getUsuarioDestinatario().getEmail())
                .tituloChamado(comentario.getChamado().getTitulo())
                .mensagem(comentario.getMensagem())
                .build();
    }
}
