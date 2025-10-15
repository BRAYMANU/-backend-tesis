package co.manuelerazo.tesis.dtos.profesionalSalud;

import lombok.Data;

@Data
public class ProfesionalSaludResponseDTO {
    private String numeroLisencia;
    private Boolean validado;
    private String especialidad;
}
