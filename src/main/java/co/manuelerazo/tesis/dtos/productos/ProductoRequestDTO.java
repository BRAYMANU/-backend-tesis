package co.manuelerazo.tesis.dtos.productos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProductoRequestDTO {
    @NotBlank(message = "el nombre del producto no puede quedar vacio")
    private String nombre;

    @NotBlank(message = "la descripcion del producto no puede quedar vacio")
    private String descripcion;

    @NotBlank(message = "la categoria del producto no puede quedar vacio")
    private String categoria;

    @NotBlank(message = "las advertencias del producto no puede quedar vacio")
    private String advertencias;





    
}
