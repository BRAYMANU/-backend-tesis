package co.manuelerazo.tesis.entitis;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Data;

@Entity
@Data
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private String descripcion;
    private String categoria;
    private String advertencias;   
    
    //relaciom
    @ManyToMany 
    @JoinTable(
        name = "producto_contenido", 
        joinColumns = @JoinColumn(name = "producto_id"), 
        inverseJoinColumns = @JoinColumn(name = "contenidoEducativo_id")
    )
    private Set<ContenidoEducativo> contenidoEducativo = new HashSet<>();
    
}
