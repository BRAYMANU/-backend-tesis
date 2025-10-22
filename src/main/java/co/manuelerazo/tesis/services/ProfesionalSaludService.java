package co.manuelerazo.tesis.services;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.manuelerazo.tesis.dtos.Publicacion.PublicacionRequestDTO;
import co.manuelerazo.tesis.dtos.Publicacion.PublicacionResponseDTO;
import co.manuelerazo.tesis.dtos.profesionalSalud.ProfesionalSaludRequestDTO;
import co.manuelerazo.tesis.dtos.profesionalSalud.ProfesionalSaludResponseDTO;
import co.manuelerazo.tesis.entitis.ProfesionalSalud;
import co.manuelerazo.tesis.entitis.Publicacion;
import co.manuelerazo.tesis.exceptions.ResourceNotFoundException;
import co.manuelerazo.tesis.repositories.ProfesionalSaludRepository;
import co.manuelerazo.tesis.repositories.PublicacionRepository;

@Service
public class ProfesionalSaludService {
    private final ProfesionalSaludRepository profesionalSaludRepository;
    private final PublicacionRepository publicacionRepository;

    public ProfesionalSaludService(ProfesionalSaludRepository profesionalSaludRepository, PublicacionRepository publicacionRepository){
        this.profesionalSaludRepository = profesionalSaludRepository;
        this.publicacionRepository = publicacionRepository;
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
    //Nota: si profesionalSalud es LAZY y estás fuera del contexto transaccional, 
    //acceder a getNombre() puede provocar LazyInitializationException. 
    //Por eso usamos @Transactional en el método PublicarContenido, la conversión y acceso ocurren dentro de la transacción. 
    //Si conviertes fuera, considera DTOs desde consulta con joins o fetch.
    
    //metodo publicar
    @Transactional
    public PublicacionResponseDTO Publicar(PublicacionRequestDTO dto){
        //1 validaciones y busquedas del profesional
        Integer profesionalId = dto.getIdProfesionalSalud();
        ProfesionalSalud profesional = profesionalSaludRepository.findById(profesionalId)
                        .orElseThrow(()-> new ResourceNotFoundException("Profesional en salud no encontrado con el Id "+profesionalId));
        
        //2.verificar que el profesional este valido
        if(profesional.getValidado() == null || !profesional.getValidado()){
            throw new IllegalArgumentException("El profesional no esta valido para pubicar contenido");
        }

        //3. Crear y poblar la entidad publicacion
        Publicacion publicacion = new Publicacion();
        publicacion.setTitulo(dto.getTitulo());
        publicacion.setContenido(dto.getContenido());
        publicacion.setFechaPublicacion(LocalDate.now());
        publicacion.setProfesionalSalud(profesional);

        //4. Guardar en la base de datos 
        Publicacion guardada = publicacionRepository.save(publicacion);

        //5. Convertir a DTO y devolver respuesta
        return convertirPublicacionA_DTO(guardada);
    }

    //metodo privado para convertir Publicacion a PublicacionResponseDTO
    private PublicacionResponseDTO convertirPublicacionA_DTO(Publicacion p){
        PublicacionResponseDTO r = new PublicacionResponseDTO();
        r.setId(p.getId());
        r.setTitulo(p.getTitulo());
        r.setContenido(p.getContenido());
        r.setFechaPublicacion(p.getFechaPublicacion());
        if(p.getProfesionalSalud() != null){
            r.setIdProfesionalSalud(p.getProfesionalSalud().getId());
            r.setNombreProfesional(p.getProfesionalSalud().getNombre());
        }
        return r;
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
