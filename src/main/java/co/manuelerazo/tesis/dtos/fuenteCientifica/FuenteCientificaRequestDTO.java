package co.manuelerazo.tesis.dtos.fuenteCientifica;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FuenteCientificaRequestDTO {
    @NotBlank(message = "El titulo de la fuente cientifica es obligatorio")
    private String titulo;

    @NotBlank(message = "El enlace de la fuente cientifica es obligatorio")
    private String enlace;  
}
