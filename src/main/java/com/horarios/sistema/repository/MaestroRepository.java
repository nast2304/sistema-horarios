

package com.horarios.sistema.repository;

import com.horarios.sistema.model.Maestro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaestroRepository
        extends JpaRepository<Maestro, Long> {

}