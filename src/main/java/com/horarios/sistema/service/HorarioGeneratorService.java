package com.horarios.sistema.service;

import com.horarios.sistema.model.*;
import com.horarios.sistema.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;
import java.time.Duration;
import java.time.LocalTime;

@Service
public class HorarioGeneratorService {

    @Autowired
    private ClaseRepository claseRepository;

    @Autowired
    private ClaseBaseRepository claseBaseRepository;

    @Autowired
    private DisponibilidadRepository disponibilidadRepository;

    
    private List<Integer> dividirHoras(int horas){

        List<Integer> bloques =
                new ArrayList<>();

        while(horas >= 2){

            bloques.add(2);

            horas -= 2;
        }

        if(horas == 1){
            bloques.add(1);
        }

        return bloques;
    }
    
    private void guardarClase(
            ClaseBase base,
            Disponibilidad horario){

        Clase clase = new Clase();

        clase.setMaestro(
                base.getMaestro()
        );

        clase.setMateria(
                base.getMateria()
        );

        clase.setGrupo(
                base.getGrupo()
        );

        clase.setAula(
                base.getAula()
        );

        clase.setDia(
                horario.getDia()
        );

        clase.setHoraInicio(
                horario.getHoraInicio()
        );

        clase.setHoraFin(
                horario.getHoraFin()
        );

        claseRepository.save(clase);
    }
    
    private boolean generaHuecoGrande(
        Long grupoId,
        String dia,
        LocalTime horaInicioNueva
){

    List<Clase> clasesDia =
            claseRepository.findAll()
            .stream()
            .filter(c ->

                    c.getGrupo().getId().equals(grupoId)

                    &&

                    c.getDia().equals(dia)
            )
            .toList();

    for(Clase c : clasesDia){

        long diferencia =
                Math.abs(
                    Duration.between(
                        c.getHoraInicio(),
                        horaInicioNueva
                    ).toHours()
                );

        if(diferencia > 2){
            return true;
        }
    }

    return false;
}
    
    public void generarHorario() {

        // Limpiar horarios anteriores
        claseRepository.deleteAll();

        // Obtener clases base
        List<ClaseBase> bases =
                claseBaseRepository.findAll();

        // Ordenar por materias con más horas
        bases.sort(
                (a, b) ->
                        Integer.compare(
                                b.getMateria().getHorasSemanales(),
                                a.getMateria().getHorasSemanales()
                        )
        );

        List<ClaseBase> pendientes =
                new ArrayList<>();

        // ============================
        // PRIMERA PASADA
        // ============================

        for (ClaseBase base : bases) {

            List<Disponibilidad> horarios =
                    disponibilidadRepository
                            .findByMaestroId(
                                    base.getMaestro().getId()
                            );

            horarios.sort(
    (a,b) -> {

        int dia =
            a.getDia().compareTo(
                b.getDia()
            );

        if(dia != 0){
            return dia;
        }

        return a.getHoraInicio()
                .compareTo(
                    b.getHoraInicio()
                );
    }
);
            
            int horasNecesarias =
                    base.getMateria()
                    .getHorasSemanales();

            List<Integer> bloques =
                    dividirHoras(
                            horasNecesarias
                    );

            int horasGeneradas = 0;

            for(Integer bloque : bloques){

    boolean asignado = false;

    for(Disponibilidad horario : horarios){

        if(horasGeneradas >= horasNecesarias){
            break;
        }

        // Conflicto maestro
        if(
            claseRepository
            .existsByMaestroIdAndDiaAndHoraInicio(
                    base.getMaestro().getId(),
                    horario.getDia(),
                    horario.getHoraInicio()
            )
        ){
            continue;
        }

        // Conflicto grupo
        if(
            claseRepository
            .existsByGrupoIdAndDiaAndHoraInicio(
                    base.getGrupo().getId(),
                    horario.getDia(),
                    horario.getHoraInicio()
            )
        ){
            continue;
        }
        
        if(
    generaHuecoGrande(
            base.getGrupo().getId(),
            horario.getDia(),
            horario.getHoraInicio()
    )
){
    continue;
}

        if(
    base.getAula() != null
    &&
    claseRepository
    .existsByAulaAndDiaAndHoraInicio(
            base.getAula(),
            horario.getDia(),
            horario.getHoraInicio()
    )
){
    continue;
}
        
        // Conflicto aula
        if(
            base.getAula() != null
            &&
            claseRepository
            .existsByAulaAndDiaAndHoraInicio(
                    base.getAula(),
                    horario.getDia(),
                    horario.getHoraInicio()
            )
        ){
            continue;
        }

        long horasGrupoDia =
                claseRepository
                .countByGrupoIdAndDia(
                        base.getGrupo().getId(),
                        horario.getDia()
                );

        if(horasGrupoDia + bloque > 3){
            continue;
        }

        long horasMaestroDia =
                claseRepository
                .countByMaestroIdAndDia(
                        base.getMaestro().getId(),
                        horario.getDia()
                );

        if(horasMaestroDia + bloque > 3){
            continue;
        }

        // ====================================
        // BLOQUE DE 2 HORAS
        // ====================================

        if(bloque == 2){

            Disponibilidad siguiente =
                    horarios.stream()
                    .filter(h ->

                            h.getDia().equals(
                                    horario.getDia()
                            )

                            &&

                            h.getHoraInicio().equals(
                                    horario.getHoraFin()
                            )
                    )
                    .findFirst()
                    .orElse(null);

            if(siguiente == null){
                continue;
            }

            // Conflicto grupo segunda hora
            if(
                claseRepository
                .existsByGrupoIdAndDiaAndHoraInicio(
                        base.getGrupo().getId(),
                        siguiente.getDia(),
                        siguiente.getHoraInicio()
                )
            ){
                continue;
            }

            // Conflicto maestro segunda hora
            if(
                claseRepository
                .existsByMaestroIdAndDiaAndHoraInicio(
                        base.getMaestro().getId(),
                        siguiente.getDia(),
                        siguiente.getHoraInicio()
                )
            ){
                continue;
            }

            // Conflicto aula segunda hora
            if(
                base.getAula() != null
                &&
                claseRepository
                .existsByAulaAndDiaAndHoraInicio(
                        base.getAula(),
                        siguiente.getDia(),
                        siguiente.getHoraInicio()
                )
            ){
                continue;
            }

            long grupoDia =
                    claseRepository
                    .countByGrupoIdAndDia(
                            base.getGrupo().getId(),
                            horario.getDia()
                    );

            if(grupoDia + 2 > 3){
                continue;
            }

            long maestroDia =
                    claseRepository
                    .countByMaestroIdAndDia(
                            base.getMaestro().getId(),
                            horario.getDia()
                    );

            if(maestroDia + 2 > 3){
                continue;
            }
            
            guardarClase(
                    base,
                    horario
            );

            guardarClase(
                    base,
                    siguiente
            );

            horasGeneradas += 2;

            asignado = true;

            break;
        }

        // ====================================
        // BLOQUE DE 1 HORA
        // ====================================

        guardarClase(
                base,
                horario
        );

        horasGeneradas++;

        asignado = true;

        break;
    }

    if(!asignado){

        System.out.println(
                "⚠ No se pudo asignar bloque de "
                + bloque
                + " horas para "
                + base.getMateria().getNombre()
                + " Grupo "
                + base.getGrupo().getNombre()
        );
    }
}

            if (horasGeneradas < horasNecesarias) {

                pendientes.add(base);

                System.out.println(
                        "Pendiente: "
                        + base.getMateria().getNombre()
                        + " Grupo "
                        + base.getGrupo().getNombre()
                        + " ("
                        + horasGeneradas
                        + "/"
                        + horasNecesarias
                        + ")"
                );
            }
        }

        // ============================
        // SEGUNDA PASADA
        // ============================

        for (ClaseBase base : pendientes) {

            long generadas =
                    claseRepository.findAll()
                            .stream()
                            .filter(c ->
                                    c.getGrupo().getId().equals(
                                            base.getGrupo().getId()
                                    )
                                    &&
                                    c.getMateria().getId().equals(
                                            base.getMateria().getId()
                                    )
                            )
                            .count();

            int faltantes =
                    base.getMateria()
                            .getHorasSemanales()
                            - (int) generadas;

            if (faltantes <= 0) {
                continue;
            }

            List<Disponibilidad> horarios =
                    disponibilidadRepository
                            .findByMaestroId(
                                    base.getMaestro().getId()
                            );

            for (Disponibilidad horario : horarios) {

                if (faltantes <= 0) {
                    break;
                }

                if (
                    claseRepository
                    .existsByMaestroIdAndDiaAndHoraInicio(
                            base.getMaestro().getId(),
                            horario.getDia(),
                            horario.getHoraInicio()
                    )
                ) {
                    continue;
                }

                if (
                    claseRepository
                    .existsByGrupoIdAndDiaAndHoraInicio(
                            base.getGrupo().getId(),
                            horario.getDia(),
                            horario.getHoraInicio()
                    )
                ) {
                    continue;
                }

                guardarClase(
                        base,
                        horario
                );

                faltantes--;
            }
        }

        System.out.println(
                "Horarios generados correctamente"
        );
    }
}