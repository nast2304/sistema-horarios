package com.horarios.sistema.controller;

import com.horarios.sistema.model.Clase;
import com.horarios.sistema.repository.ClaseRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clases")
@CrossOrigin("*")
public class ClaseController {

    @Autowired
    private ClaseRepository repository;

    // LISTAR
    @GetMapping
    public List<Clase> listar() {

        return repository.findAll();
    }

    // GUARDAR
    @PostMapping
    public Clase guardar(
            @RequestBody Clase clase) {

        return repository.save(clase);
    }

    // ELIMINAR
    @DeleteMapping("/{id}")
    public String eliminar(
            @PathVariable Long id) {

        repository.deleteById(id);

        return "Clase eliminada";
    }
}