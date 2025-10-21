package co.manuelerazo.tesis.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.manuelerazo.tesis.entitis.ProfesionalSalud;

public interface ProfesionalSaludRepository extends JpaRepository<ProfesionalSalud, Integer> {
    
    //Si llamas a findByValidado(true): te devuelve todos los profesionales aprobados.
    //Si llamas a findByValidado(false): te devuelve los profesionales que están pendientes de validación.
    //Spring Data JPA automáticamente interpreta este nombre del método y genera la consulta SQL, sin que tú escribas nada adicional.

    // Método personalizado que busca profesionales según si están validados o no
    List<ProfesionalSalud> findByValidado(Boolean validado);
    
}
