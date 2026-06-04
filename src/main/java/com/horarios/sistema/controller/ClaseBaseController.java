package com.horarios.sistema.controller;

import com.horarios.sistema.model.ClaseBase;
import com.horarios.sistema.repository.ClaseBaseRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clases_base")
@CrossOrigin("*")
public class ClaseBaseController {

    @Autowired
    private ClaseBaseRepository repository;

    // LISTAR
    @GetMapping
    public List<ClaseBase> listar(){

        return repository.findAll();
    }

    // GUARDAR
    @PostMapping
    public ClaseBase guardar(
            @RequestBody ClaseBase clase){

        return repository.save(clase);
    }

    // ELIMINAR
    @DeleteMapping("/{id}")
    public void eliminar(
            @PathVariable Long id){

        repository.deleteById(id);
    }

    // VALIDAR EXISTENCIA
    @GetMapping("/existe/{maestro}/{materia}/{grupo}")
    public boolean existeClase(

            @PathVariable Long maestro,

            @PathVariable Long materia,

            @PathVariable Long grupo){

        return repository
            .existsByMaestroIdAndMateriaIdAndGrupoId(
                    maestro,
                    materia,
                    grupo
            );
    }
}