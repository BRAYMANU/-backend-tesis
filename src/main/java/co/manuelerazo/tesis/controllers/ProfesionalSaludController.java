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

import co.manuelerazo.tesis.dtos.profesionalSalud.ProfesionalSaludRequestDTO;
import co.manuelerazo.tesis.dtos.profesionalSalud.ProfesionalSaludResponseDTO;
import co.manuelerazo.tesis.services.ProfesionalSaludService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/profesionales")
public class ProfesionalSaludController {
    private final ProfesionalSaludService profesionalSaludService;

    public ProfesionalSaludController (ProfesionalSaludService profesionalSaludService){
        this.profesionalSaludService = profesionalSaludService;
    }

    //endpoint para crear nuevo prpfesional en salud
    @PostMapping
    public ResponseEntity<ProfesionalSaludResponseDTO> CrearNuevoProfesionalEnSalud(@Valid @RequestBody ProfesionalSaludRequestDTO profesionalSaludRequestDTO){
        ProfesionalSaludResponseDTO nuevoProfesional = profesionalSaludService.CrearNuevoProfesionalEnSalud(profesionalSaludRequestDTO);

        return new ResponseEntity<>(nuevoProfesional, HttpStatus.CREATED);
    }

    //endpoin para leer todos los profesionales en salud
    @GetMapping
    public ResponseEntity<List<ProfesionalSaludResponseDTO>> ObtenerTodosLosProfesionales(){
        List<ProfesionalSaludResponseDTO> profesionales = profesionalSaludService.ObtenerTodosLosProfesionales();
        return ResponseEntity.ok(profesionales);
    }

    // El {id} en la url es una variable de ruta
    @GetMapping("/{id}")
    public ResponseEntity<ProfesionalSaludResponseDTO> ObtenerProfesionalSaludPorId (@PathVariable Integer id){
        // 1. @PathVariable extrae el valor de  {id} de la URL
        ProfesionalSaludResponseDTO profesional = profesionalSaludService.ObtenerProfesionalSaludPorId(id);
        return ResponseEntity.ok(profesional);
    }

    //editar
    @PutMapping("/{id}")
    public ResponseEntity<ProfesionalSaludResponseDTO> ActualizarProfesionalSalud (@PathVariable Integer id, @Valid @RequestBody ProfesionalSaludRequestDTO profesionalSaludRequestDTO){
        ProfesionalSaludResponseDTO profesionalActualizado = profesionalSaludService.ActualizarProfesionalSalud(id, profesionalSaludRequestDTO);
        return ResponseEntity.ok(profesionalActualizado);
    }

    //eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> EliminarProfesionalEnSalud (@PathVariable Integer id){
        profesionalSaludService.EliminarProfesionalEnSalud(id);

        return ResponseEntity.noContent().build();
    }
    
}
