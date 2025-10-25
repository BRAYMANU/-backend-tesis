package co.manuelerazo.tesis.dtos.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UsuarioRegistroRequestDTO {
    @NotBlank(message = "el nombre del usuario no puede quedar vacio")
    private String nombre;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "Debe ser un correo valido")
    private String correo;

    @NotBlank(message = "La clave es obligatoria")
    private String clave;

    /**
     * Valores permitidos:
     * - "NORMAL"
     * - "PROFESIONAL"
     */
    @NotBlank(message = "El tipo de usuario es obligatorio (NORMAL o PROFESIONAL)")
    private String tipoUsuario;

    // Campos opcionales, SOLO si tipoUsuario es PROFESIONAL
    private String numeroLicencia;
    private String especialidad;
    
}
