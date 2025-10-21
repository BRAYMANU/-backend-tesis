package co.manuelerazo.tesis.dtos.profesionalSalud;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProfesionalSaludRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @Email(message = "Debe ser un correo valido")
    @NotBlank(message = "El correo es obligatorio")
    private String correo;

    @NotBlank(message ="La clave es obligatoria")
    private String clave;

    @NotBlank(message = "el numero de licencia del doctor no puede estar vacio")
    private String numeroLisencia;

    @NotBlank(message = "la especialidad del doctor no puede estar vacia")
    private String especialidad;
}
