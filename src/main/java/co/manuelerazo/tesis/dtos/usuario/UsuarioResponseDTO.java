package co.manuelerazo.tesis.dtos.usuario;

import lombok.Data;

@Data
public class UsuarioResponseDTO {
    private Integer id;
    private String nombre;
    private String correo;
    private String clave;
    private String tipoUsuario; 
}
