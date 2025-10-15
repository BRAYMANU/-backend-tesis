package co.manuelerazo.tesis.dtos.fuenteCientifica;

import lombok.Data;

@Data
public class FuenteCientificaResponseDTO {
    private Integer id;
    private String titulo;
    private String enlace;
    private String productoNombre;      
}
