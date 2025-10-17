package co.manuelerazo.tesis.entitis;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity

public class Admin extends Usuario {
    private String nivelAcceso;        
}
 