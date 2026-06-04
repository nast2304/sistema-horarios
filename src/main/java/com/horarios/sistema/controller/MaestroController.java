package com.horarios.sistema.controller;

import com.horarios.sistema.model.Maestro;
import com.horarios.sistema.repository.MaestroRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/maestros")
@CrossOrigin("*")
public class MaestroController {

    @Autowired
    private MaestroRepository repository;

    // LISTAR
    @GetMapping
    public List<Maestro> listar() {
        return repository.findAll();
    }

    // GUARDAR
    @PostMapping
    public Maestro guardar(@RequestBody Maestro maestro) {
        return repository.save(maestro);
    }

    // BUSCAR POR ID
    @GetMapping("/{id}")
    public Optional<Maestro> buscar(@PathVariable Long id) {
        return repository.findById(id);
    }

    // EDITAR
    @PutMapping("/{id}")
    public Maestro editar(@PathVariable Long id,
                          @RequestBody Maestro maestro) {

        maestro.setId(id);

        return repository.save(maestro);
    }

    // ELIMINAR
    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable Long id) {

        repository.deleteById(id);

        return "Maestro eliminado";
    }
}