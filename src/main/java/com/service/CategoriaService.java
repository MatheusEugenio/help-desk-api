package com.service;

import com.database.model.Categoria;
import com.database.repository.CategoriaRepository;
import com.dto.ResponseCategoriaDTO;
import com.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public List<ResponseCategoriaDTO> findAll() {return categoriaRepository.findAll()
            .stream()
            .map(this::converForResponseCategoria)
            .toList();
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteCategoria(Long id) {
        categoriaRepository.deleteById(id);
    }

    public ResponseCategoriaDTO findById(Long id) throws NotFoundException {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Categoria com id =" + id + " não encontrado"));

        return converForResponseCategoria(categoria);
    }

    public ResponseCategoriaDTO addCategoria(String nomeCategoria)  {

        if (nomeCategoria.isBlank()) {
            throw new NullPointerException("Nome da categoria inválido");
        }

        Categoria categoria = Categoria.builder()
                .nomeCategoria(nomeCategoria)
                .build();

        categoriaRepository.save(categoria);
        return converForResponseCategoria(categoria);
    }

    /////////////////////////////////////
    /// PRIVATE METHODS
    /////////////////////////////////////

    private ResponseCategoriaDTO converForResponseCategoria(Categoria categoria) {
        return ResponseCategoriaDTO.builder()
                .nomeCategoria(categoria.getNomeCategoria())
                .build();
    }
}
