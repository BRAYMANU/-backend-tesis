package co.manuelerazo.tesis.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;


import co.manuelerazo.tesis.dtos.profesionalSalud.ProfesionalSaludResponseDTO;
import co.manuelerazo.tesis.entitis.ProfesionalSalud;
import co.manuelerazo.tesis.exceptions.ResourceNotFoundException;
import co.manuelerazo.tesis.repositories.ProfesionalSaludRepository;

@Service
public class AdminService {
    private  final ProfesionalSaludRepository profesionalSaludRepository;

    public AdminService(ProfesionalSaludRepository profesionalSaludRepository){
        this.profesionalSaludRepository =  profesionalSaludRepository;
    }

    // 1. Ver profesionales pendientes de validación// 
    public List<ProfesionalSaludResponseDTO> obtenerProfesionalesPendientes(){
        List<ProfesionalSalud> pendientes = profesionalSaludRepository.findByValidado(false);

        return pendientes.stream()
                        .map(this::convertirA_DTO)
                        .collect(Collectors.toList());
    }

    //2. aprobar profesional en salud
    public ProfesionalSaludResponseDTO aprobarProfesional(Integer idProfesional){
        ProfesionalSalud profesional = profesionalSaludRepository.findById(idProfesional)
            .orElseThrow(()-> new ResourceNotFoundException("Profesional no encontrado con ID: " + idProfesional));

        profesional.setValidado(true);

        ProfesionalSalud profesionalActualizado = profesionalSaludRepository.save(profesional);
        
        return convertirA_DTO(profesionalActualizado);
    }

    //metodo para convertir a DTO
    private ProfesionalSaludResponseDTO convertirA_DTO (ProfesionalSalud profesional){
        ProfesionalSaludResponseDTO dto = new ProfesionalSaludResponseDTO();
        dto.setId(profesional.getId());
        dto.setNombre(profesional.getNombre());
        dto.setCorreo(profesional.getCorreo());
        dto.setTipoUsuario(profesional.getTipoUsuario());
        dto.setNumeroLisencia(profesional.getNumeroLisencia());
        dto.setEspecialidad(profesional.getEspecialidad());
        dto.setValidado(profesional.getValidado());

        return dto;

    }

}
