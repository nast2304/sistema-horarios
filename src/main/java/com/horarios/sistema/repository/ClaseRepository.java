package com.horarios.sistema.repository;

import com.horarios.sistema.model.Clase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalTime;

public interface ClaseRepository
        extends JpaRepository<Clase, Long> {

    boolean existsByMaestroIdAndDiaAndHoraInicio(
            Long maestroId,
            String dia,
            LocalTime horaInicio
    );
    
    boolean existsByGrupoIdAndDiaAndHoraInicio(
            Long grupoId,
            String dia,
            LocalTime horaInicio
    );

    boolean existsByAulaAndDiaAndHoraInicio(
            String aula,
            String dia,
            LocalTime horaInicio
    );
    long countByGrupoIdAndDia(
            Long grupoId,
            String dia
    );
    long countByMaestroIdAndDia(
            Long maestroId,
            String dia
    );
    
    long countByGrupoIdAndMateriaId(
            Long grupoId,
            Long materiaId
    );
}