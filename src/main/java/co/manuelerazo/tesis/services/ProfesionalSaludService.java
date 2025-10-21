package co.manuelerazo.tesis.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import co.manuelerazo.tesis.dtos.profesionalSalud.ProfesionalSaludRequestDTO;
import co.manuelerazo.tesis.dtos.profesionalSalud.ProfesionalSaludResponseDTO;
import co.manuelerazo.tesis.entitis.ProfesionalSalud;
import co.manuelerazo.tesis.exceptions.ResourceNotFoundException;
import co.manuelerazo.tesis.repositories.ProfesionalSaludRepository;

@Service
public class ProfesionalSaludService {
    private final ProfesionalSaludRepository profesionalSaludRepository;

    public ProfesionalSaludService(ProfesionalSaludRepository profesionalSaludRepository){
        this.profesionalSaludRepository = profesionalSaludRepository;
    }

    //metodo para crear un nuevo profesional en salud
    public ProfesionalSaludResponseDTO CrearNuevoProfesionalEnSalud(ProfesionalSaludRequestDTO profesionalSaludRequestDTO){
        ProfesionalSalud nuevoProfesional = new ProfesionalSalud();

        //Datos heredados de usuario
        nuevoProfesional.setNombre(profesionalSaludRequestDTO.getNombre());
        nuevoProfesional.setCorreo(profesionalSaludRequestDTO.getCorreo());
        nuevoProfesional.setClave(profesionalSaludRequestDTO.getClave());
        nuevoProfesional.setTipoUsuario("PROFESIONAL_SALUD");


        //Datos propios
        nuevoProfesional.setNumeroLisencia(profesionalSaludRequestDTO.getNumeroLisencia());
        nuevoProfesional.setEspecialidad(profesionalSaludRequestDTO.getEspecialidad());
        nuevoProfesional.setValidado(false); //simepre falso al registrarse

        ProfesionalSalud profesionalGuardado = profesionalSaludRepository.save(nuevoProfesional);
        return convertirA_DTO(profesionalGuardado);
    }

    //metodo para obtener todos los profesionales en salud
    public List<ProfesionalSaludResponseDTO> ObtenerTodosLosProfesionales(){

        //obtenemos la lista de todos los profesionales en salud
        List<ProfesionalSalud> profesionales = profesionalSaludRepository.findAll();
        
        //convertios cada entidad a su Dto correspondiente
        return profesionales.stream()
                            .map(this::convertirA_DTO)
                            .collect(Collectors.toList());
    }

    //metodo para obtener profesional en salud por id 
    public ProfesionalSaludResponseDTO ObtenerProfesionalSaludPorId (Integer id){

        // 1. Buscar profesional en salud  por id. findbyid devuelve un optional
        ProfesionalSalud profesionalSalud = profesionalSaludRepository.findById(id)

                             // 2. si no lo encuentra, lanza nuestra excepcion personalizada.
                             .orElseThrow(()->new ResourceNotFoundException("Profesional en salud no encontrado con el Id: "+id));

         // 3. si la encuentra, la convierte a DTO y la devuelve
         return convertirA_DTO(profesionalSalud);

    }

    //metodo para editar profesional en salud
    public ProfesionalSaludResponseDTO ActualizarProfesionalSalud (Integer id, ProfesionalSaludRequestDTO profesionalSaludRequestDTO){
        // 1. Primero, buscar si el profesional en salud  que se quiere actualizar existe
        ProfesionalSalud profesionalExistente = profesionalSaludRepository.findById(id)
        .orElseThrow(()->new ResourceNotFoundException("Profesional en salud no encontrado con el ID: "+id));

        // 2. si existe, actualizar sus datos con los DTO.
        profesionalExistente.setNumeroLisencia(profesionalSaludRequestDTO.getNumeroLisencia());
        
        profesionalExistente.setEspecialidad(profesionalSaludRequestDTO.getEspecialidad());

        // 3. guardar la entidad actualizada en la base de datos
        ProfesionalSalud profesionalActualizado = profesionalSaludRepository.save(profesionalExistente);

        // 4. Convertir a DTO y devolver el resultado
        return convertirA_DTO(profesionalActualizado);
    }

    //metodo para eliminar profesional en salud
    public void EliminarProfesionalEnSalud(Integer id){
        // 1. verificamos que el profesional en salud  exista antes de borrarla
        if(!profesionalSaludRepository.existsById(id)){
            throw new ResourceNotFoundException("Nos se puede eliminar. Profesional no encontrado con el id: "+id);
        }
        // 2. si existe, la eliminamos
        profesionalSaludRepository.deleteById(id);
    }



    //metodo privado para reutilizar la conversion
    private ProfesionalSaludResponseDTO convertirA_DTO(ProfesionalSalud profesionalSalud){
        ProfesionalSaludResponseDTO dto = new ProfesionalSaludResponseDTO();
        dto.setId(profesionalSalud.getId());
        dto.setNombre(profesionalSalud.getNombre());
        dto.setCorreo(profesionalSalud.getCorreo());
        dto.setTipoUsuario(profesionalSalud.getTipoUsuario());
        dto.setNumeroLisencia(profesionalSalud.getNumeroLisencia());
        dto.setValidado(profesionalSalud.getValidado());
        dto.setEspecialidad(profesionalSalud.getEspecialidad());

        return dto;
    }


    

    
}
