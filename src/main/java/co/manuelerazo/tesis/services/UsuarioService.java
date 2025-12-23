package co.manuelerazo.tesis.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import co.manuelerazo.tesis.dtos.usuario.UsuarioLoginRequestDTO;
import co.manuelerazo.tesis.dtos.usuario.UsuarioRegistroRequestDTO;
import co.manuelerazo.tesis.dtos.usuario.UsuarioResponseDTO;
import co.manuelerazo.tesis.entitis.ProfesionalSalud;
import co.manuelerazo.tesis.entitis.Usuario;
import co.manuelerazo.tesis.exceptions.ResourceNotFoundException;
import co.manuelerazo.tesis.repositories.UsuarioRepository;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService (UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }

    //1. crear nuevo usuario
    public UsuarioResponseDTO CrearUsuario(UsuarioRegistroRequestDTO usuarioRequestDTO){
        Usuario nuevoUsuario = new Usuario();

        nuevoUsuario.setNombre(usuarioRequestDTO.getNombre());
        nuevoUsuario.setCorreo(usuarioRequestDTO.getCorreo());
        nuevoUsuario.setClave(usuarioRequestDTO.getClave());
        nuevoUsuario.setTipoUsuario(usuarioRequestDTO.getTipoUsuario());

        Usuario usuarioGuardado = usuarioRepository.save(nuevoUsuario);
        return convertirA_DTO(usuarioGuardado);
    }

    //2. obtener todos los usuarios
    public List<UsuarioResponseDTO> ObtenerUsuarios(){
        List<Usuario>usuarios = usuarioRepository.findAll();

        return usuarios.stream()
                .map(this::convertirA_DTO)
                .collect(Collectors.toList());
    }

    //3. para obtener ususario por id
    public UsuarioResponseDTO ObtenerUsuarioPorId (Integer id){
        //buscamos por id
        Usuario usuario = usuarioRepository.findById(id)
                        //si no lo encontramos mostramos nuestra excepcion personalizada
                        .orElseThrow(()->new ResourceNotFoundException("usuario no encontrado con el ID:"+id));

        //si lo encontramos lo retornamos y lo convertimos al dto
        return convertirA_DTO(usuario);
    }

    //4. metodo para catualizar el usuario
    public UsuarioResponseDTO ActualizarUsuario (Integer id, UsuarioRegistroRequestDTO usuarioRequestDTO){
        //buscamos por id
        Usuario usuarioExistente = usuarioRepository.findById(id)
                        //si no lo encontramos mostramos uestra excepcion personalizada
                        .orElseThrow(()->new ResourceNotFoundException("usuario no encontrado con el ID:"+id));

        //si lo encontramos lo actualizamos
        usuarioExistente.setNombre(usuarioRequestDTO.getNombre());
        usuarioExistente.setCorreo(usuarioRequestDTO.getCorreo());
        usuarioExistente.setClave(usuarioRequestDTO.getClave());
        usuarioExistente.setTipoUsuario(usuarioRequestDTO.getTipoUsuario());

        //guardamos en la base de datos
        Usuario usuarioActualizado = usuarioRepository.save(usuarioExistente);

        //retornamos y convertimos  dto
        return convertirA_DTO(usuarioActualizado);
    }
    
    //5. metodo para eliminar el usuario
    public void EliminarUsuario(Integer id){
        if(!usuarioRepository.existsById(id)){
            throw new ResourceNotFoundException("no se puede eliminar usuario no encontrado con el Id:"+id);
        }

        //si lo encontramos lo eliminamos
        usuarioRepository.deleteById(id);
    }

    //metodos de mi proyecto tesis
    //6. metodo para registrar
    public UsuarioResponseDTO Registrarse(UsuarioRegistroRequestDTO usuarioRequestDTO){

        //verificamos si ya existe un usuario con el mismo correo
    usuarioRepository.findByCorreo(usuarioRequestDTO.getCorreo()).ifPresent(u ->{
            throw new IllegalArgumentException("el correo ya esta registrado");
        });

        Usuario usuarioGuardado;

        //2 Decidir tipo de usuario
        if("PROFESIONAL".equalsIgnoreCase(usuarioRequestDTO.getTipoUsuario())){

            // 3. Validaciones exclusivas del profesional
            if(usuarioRequestDTO.getNumeroLicencia() == null || usuarioRequestDTO.getNumeroLicencia().isBlank()){
                throw new IllegalArgumentException("La licencia es obligatoria para profesionales");
            }
            if(usuarioRequestDTO.getEspecialidad() == null || usuarioRequestDTO.getEspecialidad().isBlank()){
                throw new IllegalArgumentException("La especialidad es obligatoria para profesionales");
            }

            //4. creamos el profesional de salud
            ProfesionalSalud profesional = new ProfesionalSalud();
            profesional.setNombre(usuarioRequestDTO.getNombre());
            profesional.setCorreo(usuarioRequestDTO.getCorreo());
            profesional.setClave(usuarioRequestDTO.getClave());
            profesional.setTipoUsuario("PROFESIONAL");

            profesional.setNumeroLisencia(usuarioRequestDTO.getNumeroLicencia());
            profesional.setEspecialidad(usuarioRequestDTO.getEspecialidad());
            profesional.setValidado(false); // importante 

            usuarioGuardado = usuarioRepository.save(profesional);

        } else {

            //5. creamos usuario normal
            Usuario usuario = new Usuario();
            usuario.setNombre(usuarioRequestDTO.getNombre());
            usuario.setCorreo(usuarioRequestDTO.getCorreo());
            usuario.setClave(usuarioRequestDTO.getClave());
            usuario.setTipoUsuario("NORMAL");

            usuarioGuardado = usuarioRepository.save(usuario);

        }

        return convertirA_DTO(usuarioGuardado);
        
    }

    //7. metodo para iniciar sesion
    public UsuarioResponseDTO InisiarSesion(UsuarioLoginRequestDTO usuarioLoginRequestDTO){
        
        //Buscamos al usuario por su correo
        Usuario usuario = usuarioRepository.findByCorreo(usuarioLoginRequestDTO.getCorreo())
                        .orElseThrow(()-> new ResourceNotFoundException("correo no encontrado"));
        //validamos la contraseña
        if(!usuario.getClave().equals(usuarioLoginRequestDTO.getClave())){
            throw new IllegalArgumentException("clave incorrecta");
        }

        return convertirA_DTO(usuario);
    } 


    private UsuarioResponseDTO convertirA_DTO(Usuario usuario){
        UsuarioResponseDTO dto = new UsuarioResponseDTO();

        dto.setId(usuario.getId());
        dto.setNombre(usuario.getNombre());
        dto.setCorreo(usuario.getCorreo());
        dto.setTipoUsuario(usuario.getTipoUsuario());
        return dto;
    }
    
}
