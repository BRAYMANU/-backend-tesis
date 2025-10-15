package co.manuelerazo.tesis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.manuelerazo.tesis.entitis.ContenidoEducativo;

@Repository
public interface ContenidoEducativoRepository extends JpaRepository <ContenidoEducativo, Integer>{
    
}
