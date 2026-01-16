package co.manuelerazo.tesis.entitis;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;

@Data
@Entity
public class ContenidoEducativo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String titulo;
    private String tema;
    private String contenido;  
    
    //relacion 
    @ManyToMany(mappedBy = "contenidoEducativo")
    @JsonIgnore
    private Set<Producto> productos = new HashSet<>();

}
