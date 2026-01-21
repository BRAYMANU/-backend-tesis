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

import co.manuelerazo.tesis.dtos.fuenteCientifica.FuenteCientificaRequestDTO;
import co.manuelerazo.tesis.dtos.fuenteCientifica.FuenteCientificaResponseDTO;
import co.manuelerazo.tesis.services.FuenteCientificaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/fuentes")
public class FuenteCientificaController {
    
    private final FuenteCientificaService fuenteCientificaService;
    
    public FuenteCientificaController(FuenteCientificaService fuenteCientificaService){
        this.fuenteCientificaService = fuenteCientificaService;
    }

    //1. endpoint para crear nueva fuente cientifica
    @PostMapping
    public ResponseEntity<FuenteCientificaResponseDTO>nuevaFuenteCientifica(@Valid @RequestBody FuenteCientificaRequestDTO fuenteCientificaRequestDTO){
        FuenteCientificaResponseDTO fuenteCreada = fuenteCientificaService.CrearNuevaFuenteCientifica(fuenteCientificaRequestDTO);
        return new ResponseEntity<>(fuenteCreada, HttpStatus.CREATED);
    }

    //2. endpoint para obtener todas las fuentes cientificas
    @GetMapping
    public ResponseEntity<List<FuenteCientificaResponseDTO>> ObtenerTodasLasFuentes (){
        List<FuenteCientificaResponseDTO> fuentes = fuenteCientificaService.ObtenerTodasLasFuentes();

        return ResponseEntity.ok(fuentes);
    }

    //3. endpoint para obtener fuente cientifica por id
    @GetMapping("/{id}")
    public ResponseEntity<FuenteCientificaResponseDTO> ObtenerFuenteCientificaPorId(@PathVariable Integer id){

        FuenteCientificaResponseDTO fuente = fuenteCientificaService.ObtenerFuenteCientificaPorId(id);

        return ResponseEntity.ok(fuente);
    }

    //4.endpoint para actualizar fuente cientifica
    @PutMapping("/{id}")
    public ResponseEntity<FuenteCientificaResponseDTO> ActualizarFuenteCientifica(@PathVariable Integer id, @Valid @RequestBody FuenteCientificaRequestDTO requestDTO){

        FuenteCientificaResponseDTO fuenteActualizada = fuenteCientificaService.ActualizarFuenteCientifica(id, requestDTO);

        return ResponseEntity.ok(fuenteActualizada);
    }

    //5. endpoint para eliminar fuente cientifica
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> EliminarFuenteCientifica(@PathVariable Integer id){

        fuenteCientificaService.EliminarFuenteCientifica(id);

        return ResponseEntity.noContent().build();
    }

    //6. obtener fuentes cientificas por producto id
    @GetMapping("/producto/{productoId}")
    public ResponseEntity<List<FuenteCientificaResponseDTO>> ObteneFuentesPorProducto(@PathVariable Integer id){

        List<FuenteCientificaResponseDTO> fuentes = fuenteCientificaService.ObteneFuentesPorProducto(id);

        return ResponseEntity.ok(fuentes);
    }









}
