package co.manuelerazo.tesis.services;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.manuelerazo.tesis.dtos.Publicacion.PublicacionRequestDTO;
import co.manuelerazo.tesis.dtos.Publicacion.PublicacionResponseDTO;
import co.manuelerazo.tesis.entitis.ProfesionalSalud;
import co.manuelerazo.tesis.entitis.Publicacion;
import co.manuelerazo.tesis.repositories.PublicacionRepository;
import co.manuelerazo.tesis.exceptions.ResourceNotFoundException;
import co.manuelerazo.tesis.repositories.ProfesionalSaludRepository;

@Service
public class PublicacionService {
    private final PublicacionRepository publicacionRepository;
    private final ProfesionalSaludRepository profesionalSaludRepository;

    public PublicacionService(PublicacionRepository publicacionRepository, ProfesionalSaludRepository profesionalSaludRepository){
        this.publicacionRepository = publicacionRepository;
        this.profesionalSaludRepository = profesionalSaludRepository; 
    }

    //1 metodo para crear publicacion
    @Transactional
    public PublicacionResponseDTO CrearPublicacion (PublicacionRequestDTO dto){
        
        //1. buscamos un profesional 
        ProfesionalSalud profesionalSalud = profesionalSaludRepository.findById(dto.getIdProfesionalSalud())
            .orElseThrow(()-> new ResourceNotFoundException("Profesional de salud no encontrado con id: " + dto.getIdProfesionalSalud()));

        //2. verificamos que este validado
        if(profesionalSalud.getValidado() == null || !profesionalSalud.getValidado()){
            throw new IllegalArgumentException("El profesional no está validado para publicar contenido");
        }

        //3. creamos la entidad publicacion
        Publicacion nuevaPublicacion = new Publicacion();
        nuevaPublicacion.setTitulo(dto.getTitulo());
        nuevaPublicacion.setContenido(dto.getContenido());
        nuevaPublicacion.setFechaPublicacion(LocalDate.now());
        nuevaPublicacion.setProfesionalSalud(profesionalSalud);

        //4. guardamos la publicaion
        Publicacion publicacionGuardada = publicacionRepository.save(nuevaPublicacion);

        //5. convertir a DTOS y retornamos

        return convertirA_DTO(publicacionGuardada);

    }

    //2. metodo para obtener todas las categorias
    public List<PublicacionResponseDTO> ObtenerTodasLasPublicaciones(){
        return publicacionRepository.findAll()
            .stream()
            .map(this::convertirA_DTO)
            .toList();

    }

    //3. metodo para obtener publicacion por id
    public PublicacionResponseDTO ObtenerPublicacionPorId(Integer id){
        Publicacion publicacion = publicacionRepository.findById(id)
            .orElseThrow(()-> new ResourceNotFoundException("Publicacion no encontrada con id: " + id));

            return convertirA_DTO(publicacion);
    }

    //4. metodo para obtener publicacions por profesional de salud
    public List<PublicacionResponseDTO> ObtenerPublicacionesPorProfesionalSalud(Integer idProfesional){
        ProfesionalSalud profesional = profesionalSaludRepository.findById(idProfesional)
            .orElseThrow(() -> new ResourceNotFoundException("Profesional no encontrado con id: " + idProfesional));
        
        return publicacionRepository.findByProfesionalSalud(profesional)
            .stream()   
            .map(this::convertirA_DTO)
            .collect(Collectors.toList());
    }

    //5. actualizar publicacion
    @Transactional
    public PublicacionResponseDTO ActualizarPublicacion(Integer idPubliccacion, Integer idProfesional, PublicacionRequestDTO dto){

        Publicacion publicacion = publicacionRepository.findById(idPubliccacion)
            .orElseThrow(()-> new ResourceNotFoundException("Publicacion no encontrada con id: " + idPubliccacion));

        if(!publicacion.getProfesionalSalud().getId().equals(idProfesional)){
            throw new IllegalArgumentException("El profesional no está autorizado para actualizar esta publicación");
        }

        publicacion.setTitulo(dto.getTitulo());
        publicacion.setContenido(dto.getContenido());

        return convertirA_DTO(publicacion);
    }

    //6.eliminar publicacion
    public void EliminarPublicacion (Integer idPublicacion, Integer idProfesional){
        Publicacion publicacionExistente = publicacionRepository.findById(idPublicacion)
            .orElseThrow(()-> new ResourceNotFoundException("Publicacion no encontrada con id: " + idPublicacion));

        if(!publicacionExistente.getProfesionalSalud().getId().equals(idProfesional)){
            throw new IllegalArgumentException("El profesional no está autorizado para eliminar esta publicación");
        }

        publicacionRepository.delete(publicacionExistente);
    }






    
    //metodo privado para convertir a DTO
    private PublicacionResponseDTO convertirA_DTO(Publicacion p){
        PublicacionResponseDTO dto = new PublicacionResponseDTO();

        dto.setId(p.getId());
        dto.setTitulo(p.getTitulo());
        dto.setContenido(p.getContenido());
        dto.setFechaPublicacion(p.getFechaPublicacion());

        dto.setIdProfesionalSalud(p.getProfesionalSalud().getId());
        dto.setNombreProfesional(p.getProfesionalSalud().getNombre());

        return dto;

    }

    
}
