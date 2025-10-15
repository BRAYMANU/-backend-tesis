package co.manuelerazo.tesis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import co.manuelerazo.tesis.entitis.ProfesionalSalud;

public interface ProfesionalSaludRepository extends JpaRepository<ProfesionalSalud, Integer> {
    
}
