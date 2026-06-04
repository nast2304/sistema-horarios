package com.horarios.sistema.controller;

import com.horarios.sistema.model.Materia;
import com.horarios.sistema.repository.MateriaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/materias")
@CrossOrigin("*")
public class MateriaController {

    @Autowired
    private MateriaRepository repository;

    // LISTAR
    @GetMapping
    public List<Materia> listar() {

        return repository.findAll();
    }

    // GUARDAR
    @PostMapping
    public Materia guardar(@RequestBody Materia materia) {

        return repository.save(materia);
    }

    // BUSCAR
    @GetMapping("/{id}")
    public Optional<Materia> buscar(@PathVariable Long id) {

        return repository.findById(id);
    }

    // EDITAR
    @PutMapping("/{id}")
    public Materia editar(@PathVariable Long id,
                           @RequestBody Materia materia) {

        materia.setId(id);

        return repository.save(materia);
    }

    // ELIMINAR
    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable Long id) {

        repository.deleteById(id);

        return "Materia eliminada";
    }
}