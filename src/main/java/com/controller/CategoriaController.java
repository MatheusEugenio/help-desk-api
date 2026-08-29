package com.controller;

import com.dto.ResponseCategoriaDTO;
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
    public List<ResponseCategoriaDTO> findAll() {return categoriaService.findAll();}

    @GetMapping("/id-categoria/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseCategoriaDTO viewCategoriaByID(@Valid @PathVariable Long id) throws NotFoundException {
        return categoriaService.findById(id);
    }

    @PostMapping("/add/{nomeCategoria}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseCategoriaDTO addCategoria(@Valid @PathVariable String nomeCategoria) {
        return categoriaService.addCategoria(nomeCategoria);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCategoria(@Valid @PathVariable Long id){
        categoriaService.deleteCategoria(id);
    }
}
