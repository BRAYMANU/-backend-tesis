package co.manuelerazo.tesis.dtos.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UsuarioLoginRequestDTO {
    @NotBlank(message = "el correo no puede estar vacio")
    @Email(message = "Debe ser un correo valido")
    private String correo;

    @NotBlank(message = "la clave no puede estar vacia")
    private String clave;
    
}
