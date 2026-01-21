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

@Entity
@Data
public class FuenteCientifica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String titulo;
    private String enlace;
    
    //Una fuente científica puede respaldar múltiples productos
    @ManyToMany(mappedBy = "fuenteCientificas")
    @JsonIgnore
    private Set<Producto> productos = new HashSet<>();
}
