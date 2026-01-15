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
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;
    private String nombre;
    private String descripcion;

    //relacion (inversa) muchos a muchos con publicacion
    @JsonIgnore
    @ManyToMany(mappedBy = "categorias")//esta categoria 
    private Set<Publicacion> publicaciones = new HashSet<>();

    //relacion (inversa) muchos a muchos con producto
    @JsonIgnore
    @ManyToMany(mappedBy = "categorias")
    private Set<Producto> productos = new HashSet<>();




    
}
