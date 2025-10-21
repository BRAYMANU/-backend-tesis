package co.manuelerazo.tesis.dtos.profesionalSalud;

import lombok.Data;

@Data
public class ProfesionalSaludResponseDTO {
    private Integer id;         //heredado de usuario
    private String nombre;      //heredado de usuario
    private String correo;      //heredado de usuario
    private String tipoUsuario; //heredado de usuario

    private String numeroLisencia;
    private Boolean validado;
    private String especialidad;
}
