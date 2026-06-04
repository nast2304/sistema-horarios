

package com.horarios.sistema.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "maestros")
public class Maestro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String correo;

    private String password;
}