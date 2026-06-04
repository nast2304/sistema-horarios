package com.horarios.sistema.controller;

import com.horarios.sistema.service.HorarioGeneratorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/horario")
@CrossOrigin("*")
public class HorarioController {

    @Autowired
    private HorarioGeneratorService service;

    @PostMapping("/generar")
    public String generarHorario(){

        service.generarHorario();

        return "Horario generado";
    }
}