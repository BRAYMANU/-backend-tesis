package co.manuelerazo.tesis.entitis;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity

public class ProfesionalSalud extends Usuario {
    private String numeroLisencia;
    private Boolean validado;
    private String especialidad;


    @JsonManagedReference //Indica a Jackson que este es el lado "padre" de la relación. Evita bucles infinitos al convertir a JSON.
    // Relación Uno a Muchos: Un profesional tiene muchas publicaciones
    @OneToMany(
        mappedBy = "profesionalSalud",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )  
    private List<Publicacion> publicaciones;
}
