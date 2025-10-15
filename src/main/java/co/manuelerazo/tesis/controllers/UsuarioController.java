package co.manuelerazo.tesis.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.manuelerazo.tesis.dtos.usuario.UsuarioLoginRequestDTO;
import co.manuelerazo.tesis.dtos.usuario.UsuarioRequestDTO;
import co.manuelerazo.tesis.dtos.usuario.UsuarioResponseDTO;
import co.manuelerazo.tesis.services.UsuarioService;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController (UsuarioService usuarioService){
        this.usuarioService = usuarioService;
    }

    //para crear un nuevo usuario de tipo post
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> CrearUsuario (@Valid @RequestBody UsuarioRequestDTO usuarioRequestDTO){
        UsuarioResponseDTO nuevoUsuario = usuarioService.CrearUsuario(usuarioRequestDTO);
        return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED);
    }

    //para leer todos los usuarios que esxisten en la base de datos
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> ObtenerUsuarios (){
        List<UsuarioResponseDTO> usuarios = usuarioService.ObtenerUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    //para buscar por id
    //el {id} en la url es una variable de ruta
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> ObtenerUsuarioPorId (@PathVariable Integer id){
        UsuarioResponseDTO usuario = usuarioService.ObtenerUsuarioPorId(id);
        return ResponseEntity.ok(usuario);
    }
    //para editar
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> EliminarUsuario (@PathVariable Integer id, @Valid @RequestBody UsuarioRequestDTO usuarioRequestDTO){
        UsuarioResponseDTO usuarioActualizado = usuarioService.ActualizarUsuario(id, usuarioRequestDTO);
        return ResponseEntity.ok(usuarioActualizado);
    }

    //para eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> EliminarUsuario (@PathVariable Integer id){
        usuarioService.EliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    // POST /api/usuarios/registrar
    @PostMapping("/registrar")
    public ResponseEntity<UsuarioResponseDTO> Registrarse(@Valid @RequestBody UsuarioRequestDTO usuarioRequestDTO){
        UsuarioResponseDTO usuario = usuarioService.Registrarse(usuarioRequestDTO);
        return new ResponseEntity<>(usuario, HttpStatus.CREATED);
    }

    // POST /api/usuarios/login
    @PostMapping("/login")
    public ResponseEntity<UsuarioResponseDTO> InisiarSesion (@Valid @RequestBody UsuarioLoginRequestDTO usuarioLoginRequestDTO){
        UsuarioResponseDTO usuario = usuarioService.InisiarSesion(usuarioLoginRequestDTO);
        return ResponseEntity.ok(usuario);

    }


    }

    

    

