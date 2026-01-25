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
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;
    
    private String nombre;
    private String descripcion;
    private String advertencias;   
    
    
    //relacion de muchos a muchos con ContenidoEducativo
    @ManyToMany 
    @JoinTable(
        name = "producto_contenido", 
        joinColumns = @JoinColumn(name = "producto_id"), 
        inverseJoinColumns = @JoinColumn(name = "contenidoEducativo_id")
    )
    private Set<ContenidoEducativo> contenidoEducativo = new HashSet<>();
    
    //relacion de muchos a muchos con FuenteCientifica
    @ManyToMany
    @JoinTable(
        name = "producto_fuente_cientifica",
        joinColumns = @JoinColumn(name = "producto_id"),
        inverseJoinColumns = @JoinColumn(name = "fuente_cientifica_id")
    )
    private Set<FuenteCientifica> fuenteCientificas = new HashSet<>();

    //relacion de muchos a muchos con Categoria
    @ManyToMany
    @JoinTable(
        name = "producto_categoria",
        joinColumns = @JoinColumn(name = "producto_id"),
        inverseJoinColumns = @JoinColumn(name = "categoria_id")
    )
    private Set<Categoria> categorias = new HashSet<>();
}

