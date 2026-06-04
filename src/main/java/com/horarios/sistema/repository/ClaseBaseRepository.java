package com.horarios.sistema.repository;

import com.horarios.sistema.model.ClaseBase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClaseBaseRepository
        extends JpaRepository<ClaseBase, Long> {

    boolean existsByMaestroIdAndMateriaIdAndGrupoId(
            Long maestroId,
            Long materiaId,
            Long grupoId
    );
}