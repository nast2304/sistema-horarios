package com.horarios.sistema.repository;

import com.horarios.sistema.model.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GrupoRepository
        extends JpaRepository<Grupo, Long> {

}