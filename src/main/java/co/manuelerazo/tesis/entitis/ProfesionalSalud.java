package co.manuelerazo.tesis.entitis;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity

public class ProfesionalSalud extends Usuario {
    private String numeroLisencia;
    private Boolean validado;
    private String especialidad;
    
}
