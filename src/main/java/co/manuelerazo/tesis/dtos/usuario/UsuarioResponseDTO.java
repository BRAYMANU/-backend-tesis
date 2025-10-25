package co.manuelerazo.tesis.dtos.usuario;

import lombok.Data;

@Data
public class UsuarioResponseDTO {
    private Integer id;
    private String nombre;
    private String correo;
    private String tipoUsuario; 

    // Para el caso de profesional
    private String numeroLicencia;
    private String especialidad;
    private Boolean validado;  // solo si aplica

}
