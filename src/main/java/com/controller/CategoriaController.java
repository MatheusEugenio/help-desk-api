package com.controller;

import com.database.model.Categoria;
import com.dto.ResponseChamadoDTO;
import com.exception.NotFoundException;
import com.service.CategoriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/categorias")
@Validated
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Categoria> findAll() {return categoriaService.findAll();}

    @GetMapping("/id-chamado/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Categoria viewChamadoByID(@Valid @PathVariable Long id) throws NotFoundException {
        return categoriaService.findById(id);
    }

    @PostMapping("/add/{nomeCategoria}")
    @ResponseStatus(HttpStatus.CREATED)
    public void addCategoria(@Valid @PathVariable String nomeCategoria) {
            categoriaService.addCategoria(nomeCategoria);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCategoria(@Valid @PathVariable Long id){
        categoriaService.deleteCategoria(id);
    }
}
