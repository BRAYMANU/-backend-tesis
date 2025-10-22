package co.manuelerazo.tesis.dtos.Publicacion;

import java.time.LocalDate;

import lombok.Data;

@Data
public class PublicacionResponseDTO {
    private Integer id;
    private String titulo;
    private String contenido;
    private LocalDate fechaPublicacion;

    //Informacion basica del profesional que creo la publicacion 

    private Integer idProfesionalSalud;
    private String nombreProfesional;
}
