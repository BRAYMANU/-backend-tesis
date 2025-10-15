package co.manuelerazo.tesis.dtos.productos;

import lombok.Data;

@Data
public class ProductoResponseDTO {
    private Integer id;
    private String nombre;
    private String descripcion;
    private String categoria;
    private String advertencias; 
}
