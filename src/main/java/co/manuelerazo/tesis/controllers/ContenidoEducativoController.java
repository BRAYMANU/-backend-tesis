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

import co.manuelerazo.tesis.dtos.contenidoEducativo.ContenidoEducativoRequestDTO;
import co.manuelerazo.tesis.dtos.contenidoEducativo.ContenidoEducativoResponseDTO;
import co.manuelerazo.tesis.services.ContenidoEducativoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/contenidos")
public class ContenidoEducativoController {
    private final ContenidoEducativoService contenidoEducativoService;

    public ContenidoEducativoController (ContenidoEducativoService contenidoEducativoService){
        this.contenidoEducativoService = contenidoEducativoService;
    }

    //endpoint para crear un nuevo contenido
    @PostMapping
    public ResponseEntity<ContenidoEducativoResponseDTO> nuevoContenido(@Valid @RequestBody ContenidoEducativoRequestDTO contenidoEducativoRequestDTO){
        ContenidoEducativoResponseDTO nuevoContenido = contenidoEducativoService.CrearNuevoContenido(contenidoEducativoRequestDTO);
        return new ResponseEntity<>(nuevoContenido, HttpStatus.CREATED);
    }

    //endpoint para mostrar todo el contenido
    @GetMapping
    public ResponseEntity<List<ContenidoEducativoResponseDTO>> ObtenerTodoElContenido (){
        List<ContenidoEducativoResponseDTO> contenidos = contenidoEducativoService.ObtenerTodoElContenido();
        return ResponseEntity.ok(contenidos);
    }

    //endpoint para traer contenido por id
    // El {id} en la url es una variable de ruta
    @GetMapping("/{id}")
    public ResponseEntity<ContenidoEducativoResponseDTO> ObtenerContenidoPorId (@PathVariable Integer id){
        // 1. @PathVariable extrae el valor de  {id} de la URL
        ContenidoEducativoResponseDTO contenido = contenidoEducativoService.ObtenerContenidoPorId(id);
        return ResponseEntity.ok(contenido);

    }

    //Editar
    @PutMapping("/{id}")
    public ResponseEntity<ContenidoEducativoResponseDTO> ModificarContenido(@PathVariable Integer id, @Valid @RequestBody ContenidoEducativoRequestDTO contenidoEducativoRequestDTO){
        ContenidoEducativoResponseDTO contenidoActualizado = contenidoEducativoService.ModificarContenido(id, contenidoEducativoRequestDTO);
        return ResponseEntity.ok(contenidoActualizado);
    }

    //Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> EliminarContenido(@PathVariable Integer id){
        contenidoEducativoService.EliminarContenido(id);

        return ResponseEntity.noContent().build();
    }
     
}
