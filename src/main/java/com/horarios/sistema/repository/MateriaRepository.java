package com.horarios.sistema.repository;

import com.horarios.sistema.model.Materia;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MateriaRepository
        extends JpaRepository<Materia, Long> {

}