package com.horarios.sistema.repository;

import com.horarios.sistema.model.Disponibilidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import jakarta.transaction.Transactional;

public interface DisponibilidadRepository extends JpaRepository<Disponibilidad, Long> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Disponibilidad d WHERE d.maestro.id = :maestroId")
    void deleteByMaestroId(Long maestroId);

    List<Disponibilidad> findByMaestroId(Long maestroId);
}