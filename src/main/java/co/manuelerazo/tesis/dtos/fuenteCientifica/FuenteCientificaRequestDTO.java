package co.manuelerazo.tesis.dtos.fuenteCientifica;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FuenteCientificaRequestDTO {
    @NotBlank(message = "El titulo de la fuente cientifica es obligatorio")
    private String titulo;

    @NotBlank(message = "El enlace de la fuente cientifica es obligatorio")
    private String enlace;

    @NotNull(message = "eL ID del producto asociado es obligatorio")
    private Integer producto_id;
    
}
