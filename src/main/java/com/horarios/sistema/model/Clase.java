package com.horarios.sistema.model;

import jakarta.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "clases")
public class Clase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String dia;

    @Column(name = "hora_inicio")
    private LocalTime horaInicio;

    @Column(name = "hora_fin")
    private LocalTime horaFin;

    private String aula;

    @ManyToOne
    @JoinColumn(name = "maestro_id")
    private Maestro maestro;

    @ManyToOne
    @JoinColumn(name = "materia_id")
    private Materia materia;

    @ManyToOne
    @JoinColumn(name = "grupo_id")
    private Grupo grupo;

    // ===== GETTERS =====

    public Long getId() {
        return id;
    }

    public String getDia() {
        return dia;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public String getAula() {
        return aula;
    }

    public Maestro getMaestro() {
        return maestro;
    }

    public Materia getMateria() {
        return materia;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    // ===== SETTERS =====

    public void setId(Long id) {
        this.id = id;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    public void setAula(String aula) {
        this.aula = aula;
    }

    public void setMaestro(Maestro maestro) {
        this.maestro = maestro;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }
}