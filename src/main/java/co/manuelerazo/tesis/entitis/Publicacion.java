package co.manuelerazo.tesis.entitis;


import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Publicacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;
    private String titulo;
    private String contenido;
    private LocalDate fechaPublicacion;

    @JsonBackReference //Indica que este es el lado "hijo" de la relación. Evita que se genere JSON infinito entre padre ↔ hijo.
    
    // Relación Muchos a Uno: Muchas publicaciones pertenecen a un profesional
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profesional_salud_id", referencedColumnName = "id")//Esto crea una columna en la tabla publicacion llamada profesional_salud_id, que almacena el ID del profesional.
    private ProfesionalSalud profesionalSalud; 
}
