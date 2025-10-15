package co.manuelerazo.tesis.dtos.contenidoEducativo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ContenidoEducativoRequestDTO {
    @NotBlank(message = "el titulo del contenido no puede estar vacio.")
    private String titulo;

    @NotBlank(message = "el tema del contenido no puede estar vacio.")
    private String tema;

    @NotBlank(message = "el contenido no puede estar vacio.")
    private String contenido;
  
}
