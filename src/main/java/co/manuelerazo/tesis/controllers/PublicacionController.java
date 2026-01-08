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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.manuelerazo.tesis.dtos.Publicacion.PublicacionRequestDTO;
import co.manuelerazo.tesis.dtos.Publicacion.PublicacionResponseDTO;
import co.manuelerazo.tesis.services.PublicacionService;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/publicaciones")
public class PublicacionController {

    private final PublicacionService publicacionService;
    public PublicacionController(PublicacionService publicacionService){
        this.publicacionService = publicacionService;
    }

    //1. crear publicacion
    @PostMapping
    public ResponseEntity<PublicacionResponseDTO> CrearPublicacion (@Valid @RequestBody PublicacionRequestDTO dto ){
        PublicacionResponseDTO respuesta = publicacionService.CrearPublicacion(dto);
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }

    //2. obtener todas las publicaciones 
    @GetMapping
    public ResponseEntity<List<PublicacionResponseDTO>> ObtenerTodasLasPublicaciones(){
        return ResponseEntity.ok(publicacionService.ObtenerTodasLasPublicaciones());
    }

    //3. obtener publicaciones por id
    @GetMapping("/{id}")
    public ResponseEntity<PublicacionResponseDTO> ObtenerPublicacionPorId(@PathVariable Integer id){
        return ResponseEntity.ok(publicacionService.ObtenerPublicacionPorId(id));    
    }

    //4. obtener publicaciones por profesional
    @GetMapping("/profesional/{idProfesional}")
    public ResponseEntity<List<PublicacionResponseDTO>> ObtenerPublicacionesPorProfesionalSalud(@PathVariable Integer idProfesional){
        return ResponseEntity.ok(publicacionService.ObtenerPublicacionesPorProfesionalSalud(idProfesional));
    }

    //5. actualizar publicacion
    @PutMapping("/{idPublicacion}")
    public ResponseEntity<PublicacionResponseDTO> ActualizarPublicacion (@PathVariable Integer idPublicacion, @RequestParam Integer idProfesional, @Valid @RequestBody PublicacionRequestDTO dto ){
        return ResponseEntity.ok(publicacionService.ActualizarPublicacion(idPublicacion, idProfesional, dto));
    }

    //6. eliminar publicacion
    @DeleteMapping("/{idPublicacion}")
    public ResponseEntity<Void> EliminarPublicacion (@PathVariable Integer idPublicacion, @RequestParam Integer idProfesional){
        publicacionService.EliminarPublicacion(idPublicacion, idProfesional);
        return ResponseEntity.noContent().build();
    }


    
}
