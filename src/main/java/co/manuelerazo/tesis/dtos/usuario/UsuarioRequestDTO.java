package co.manuelerazo.tesis.dtos.usuario;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UsuarioRequestDTO {
    @NotBlank(message = "el nombre del usuario no puede quedar vacio")
    private String nombre;

    @NotBlank(message = "el correo del usuario no puede quedar vacio")
    private String correo;

    @NotBlank(message = "es obligatorio la clave")
    private String clave;

    
    private String tipoUsuario;
}
