package co.manuelerazo.tesis.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import co.manuelerazo.tesis.dtos.contenidoEducativo.ContenidoEducativoRequestDTO;
import co.manuelerazo.tesis.dtos.contenidoEducativo.ContenidoEducativoResponseDTO;
import co.manuelerazo.tesis.entitis.ContenidoEducativo;
import co.manuelerazo.tesis.exceptions.ResourceNotFoundException;
import co.manuelerazo.tesis.repositories.ContenidoEducativoRepository;

@Service
public class ContenidoEducativoService {
    private final ContenidoEducativoRepository contenidoEducativoRepository;

    public ContenidoEducativoService (ContenidoEducativoRepository contenidoEducativoRepository){
        this.contenidoEducativoRepository = contenidoEducativoRepository;
    }

    //crear un nuevo contenido educativo
    public ContenidoEducativoResponseDTO CrearNuevoContenido (ContenidoEducativoRequestDTO contenidoEducativoRequestDTO){
        ContenidoEducativo nuevoContenido = new ContenidoEducativo();

        //creamos el nuevo contenido
        nuevoContenido.setTitulo(contenidoEducativoRequestDTO.getTitulo());
        nuevoContenido.setTema(contenidoEducativoRequestDTO.getTema());
        nuevoContenido.setContenido(contenidoEducativoRequestDTO.getContenido());

        //guardamos en la base de datos
        ContenidoEducativo contenidoGuardado = contenidoEducativoRepository.save(nuevoContenido);

        return convertirA_DTO(contenidoGuardado);
    }

    //metodo para obtener todo el contenido educativo
    public List<ContenidoEducativoResponseDTO> ObtenerTodoElContenido(){
        List<ContenidoEducativo> contenidos = contenidoEducativoRepository.findAll();

        return contenidos.stream()
                .map(this::convertirA_DTO)
                .collect(Collectors.toList());
    }

    //metodo para obtener un contenido por id
    public ContenidoEducativoResponseDTO ObtenerContenidoPorId (Integer id){
        //buscamos contenido por id
        ContenidoEducativo contenidoEducativo = contenidoEducativoRepository.findById(id)
                            //si no lo encuentra mostramos la excepcion personalizada
                            .orElseThrow(()-> new ResourceNotFoundException("Contenido educativo no encontrado por el ID: "+id));
        
        //mostramos el contenido
        return convertirA_DTO(contenidoEducativo);
    }

    //metodo para modificar el contenido educativo
    public ContenidoEducativoResponseDTO ModificarContenido (Integer id, ContenidoEducativoRequestDTO contenidoEducativoRequestDTO){
        //buscamos contenido por id
        ContenidoEducativo contenidoEducativoExistente = contenidoEducativoRepository.findById(id)
                            //si no lo encuentra mostramos la excepcion personalizada
                            .orElseThrow(()-> new ResourceNotFoundException("Contenido educativo no encontrado por el ID: "+id));
        
        //si lo encuentra modificamos el contenido
        contenidoEducativoExistente.setTitulo(contenidoEducativoRequestDTO.getTitulo());
        contenidoEducativoExistente.setTema(contenidoEducativoRequestDTO.getTema());
        contenidoEducativoExistente.setContenido(contenidoEducativoRequestDTO.getContenido());

        //guardamos los cambios en la base de datos
        ContenidoEducativo contenidoModificado = contenidoEducativoRepository.save(contenidoEducativoExistente);

        //Convertir a DTO y devolver el resultado
        return convertirA_DTO(contenidoModificado);
    }

    //metodo para eliminar el contenido educativo
    public void EliminarContenido (Integer id){
        //verificamos si el contenido existe
        if(!contenidoEducativoRepository.existsById(id)){
            throw new ResourceNotFoundException("No se puede eliminar. contenido no encontrado con ID: "+id);
        }
        contenidoEducativoRepository.deleteById(id);

    }

     // metodo privado para reutilizar la conversion
    private ContenidoEducativoResponseDTO convertirA_DTO(ContenidoEducativo contenidoEducativo){
        ContenidoEducativoResponseDTO dto = new ContenidoEducativoResponseDTO();
        dto.setId(contenidoEducativo.getId());
        dto.setTitulo(contenidoEducativo.getTitulo());
        dto.setTema(contenidoEducativo.getTema());
        dto.setContenido(contenidoEducativo.getContenido());

        return dto;
    }

}
