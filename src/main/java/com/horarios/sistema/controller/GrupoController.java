package com.horarios.sistema.controller;

import com.horarios.sistema.model.Grupo;
import com.horarios.sistema.repository.GrupoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grupos")
@CrossOrigin("*")
public class GrupoController {

    @Autowired
    private GrupoRepository repository;

    // LISTAR
    @GetMapping
    public List<Grupo> listar() {

        return repository.findAll();
    }

    // GUARDAR
    @PostMapping
    public Grupo guardar(
            @RequestBody Grupo grupo) {

        return repository.save(grupo);
    }

    // EDITAR
    @PutMapping("/{id}")
    public Grupo editar(
            @PathVariable Long id,
            @RequestBody Grupo grupo) {

        grupo.setId(id);

        return repository.save(grupo);
    }

    // ELIMINAR
    @DeleteMapping("/{id}")
    public String eliminar(
            @PathVariable Long id) {

        repository.deleteById(id);

        return "Grupo eliminado";
    }
}