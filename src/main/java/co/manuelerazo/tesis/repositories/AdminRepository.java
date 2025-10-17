package co.manuelerazo.tesis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.manuelerazo.tesis.entitis.Admin;

@Repository
public interface AdminRepository extends JpaRepository <Admin, Integer> {
    
}
