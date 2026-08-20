package com.service;

import com.database.model.Categoria;
import com.database.repository.ICategoriaRepository;
import com.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final ICategoriaRepository categoriaRepository;

    public List<Categoria> findAll() {return categoriaRepository.findAll();}

    @Transactional(rollbackFor = Exception.class)
    public void deleteCategoria(Long id) {
        categoriaRepository.deleteById(id);
    }

    public Categoria findById(Long id) throws NotFoundException {
        Categoria categoria = categoriaRepository.findById(id)
                .orElse(null);

        if  (categoria == null) {
            throw new NotFoundException("Categoria com id ="+id+" não encontrado");
        }

        return categoria;
    }

    public void addCategoria(String nomeCategoria)  {

        if (nomeCategoria.isBlank()) {
            throw new NullPointerException("Nome da categoria inválido");
        }

        Categoria categoria = Categoria.builder()
                .nomeCategoria(nomeCategoria)
                .build();

        categoriaRepository.save(categoria);
    }

}
