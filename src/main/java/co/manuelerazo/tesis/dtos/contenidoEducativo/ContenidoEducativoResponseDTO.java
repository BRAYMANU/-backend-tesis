package co.manuelerazo.tesis.dtos.contenidoEducativo;

import lombok.Data;

@Data
public class ContenidoEducativoResponseDTO {
    private Integer id;
    private String titulo;
    private String tema;
    private String contenido;  
}
