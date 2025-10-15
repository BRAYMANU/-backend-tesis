package co.manuelerazo.tesis.dtos.profesionalSalud;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProfesionalSaludRequestDTO {
    @NotNull(message = "el numero de lisencia del doctor no puede estar vacio")
    private String numeroLisencia;

    @NotNull(message = "validado es obligatorio")
    private Boolean validado;

    @NotNull(message = "la especialidad del doctor no puede estar vacia")
    private String especialidad;
}
