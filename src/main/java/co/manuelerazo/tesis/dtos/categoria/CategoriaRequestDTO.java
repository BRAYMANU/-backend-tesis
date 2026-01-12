package co.manuelerazo.tesis.dtos.categoria;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoriaRequestDTO {

    @NotBlank(message = "El nombre de la categoria es obligatorio")
    private String nombre;

    private String descripcion;  
}
