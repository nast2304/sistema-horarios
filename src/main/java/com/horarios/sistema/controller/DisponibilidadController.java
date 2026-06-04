package com.horarios.sistema.controller;

import com.horarios.sistema.model.Disponibilidad;
import com.horarios.sistema.repository.DisponibilidadRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/disponibilidad")
@CrossOrigin("*")
public class DisponibilidadController {

    @Autowired
    private DisponibilidadRepository repository;

    // LISTAR
    @GetMapping
    public List<Disponibilidad> listar() {

        return repository.findAll();
    }

    // GUARDAR
    @PostMapping
    public Disponibilidad guardar(
            @RequestBody Disponibilidad disponibilidad) {

        return repository.save(disponibilidad);
    }

    // ELIMINAR
    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable Long id) {

        repository.deleteById(id);

        return "Disponibilidad eliminada";
    }
    
    @DeleteMapping("/maestro/{id}")
    public String eliminarPorMaestro(
            @PathVariable Long id){

        repository.deleteByMaestroId(id);

        return "Horario eliminado";
    }
    
    @GetMapping("/maestro/{id}")
    public List<Disponibilidad> obtenerPorMaestro(
            @PathVariable Long id){

        return repository.findByMaestroId(id);
    }
}