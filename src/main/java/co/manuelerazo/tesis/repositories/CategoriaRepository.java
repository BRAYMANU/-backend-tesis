package co.manuelerazo.tesis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import co.manuelerazo.tesis.entitis.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
    boolean existsByNombre(String nombre);
    
    /*existsByNombre:

Evita categorías duplicadas

Regla de negocio básica */
    
}
