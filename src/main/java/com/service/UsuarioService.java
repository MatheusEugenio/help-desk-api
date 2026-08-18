package com.service;

import com.database.enums.PapelUsuarioEnum;
import com.database.model.UsuarioModel;
import com.database.repository.IUsuarioRepository;
import com.dto.UsuarioDTO;
import com.exception.AlreadyExistsException;
import com.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final IUsuarioRepository usuarioRepository;

    public List<UsuarioModel> findAll() {return usuarioRepository.findAll();}

    public UsuarioDTO createdUsuario(UsuarioDTO usuarioDTO) throws AlreadyExistsException {

        UsuarioModel usuario = usuarioRepository.findByEmail(usuarioDTO.getEmail())
                .orElse(null);

        if (usuario != null){
            throw new AlreadyExistsException("Já existe um usuário com esse email");
        }

        usuario = UsuarioModel.builder()
                .nome(usuarioDTO.getNome())
                .email(usuarioDTO.getEmail())
                .papel(usuarioDTO.getPapel())
                .build();

        usuarioRepository.save(usuario);

        return convertForUsuarioDTO(usuario);
    }

    public void remove(Long id) {usuarioRepository.deleteById(id);}

    public UsuarioDTO updatePapelUsuario(Long id, PapelUsuarioEnum novoPapelUsuario) throws NotFoundException {

        UsuarioModel usuario = usuarioRepository.findById(id)
                .orElse(null);

        if (usuario == null) {
            throw new NotFoundException("Usuario não encontrado!");
        }

        usuario.setPapel(novoPapelUsuario);

        usuarioRepository.save(usuario);
        return convertForUsuarioDTO(usuario);
    }

    /////////////////////////////////////
    /// PRIVATE METHODS
    /////////////////////////////////////

    private UsuarioDTO convertForUsuarioDTO(UsuarioModel user) {
        return UsuarioDTO.builder()
                .nome(user.getNome())
                .email(user.getEmail())
                .papel(user.getPapel())
                .build();
    }
}
