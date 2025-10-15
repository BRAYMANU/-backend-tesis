package co.manuelerazo.tesis.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

    @PostMapping
    public ResponseEntity<FuenteCientificaResponseDTO>nuevaFuenteCientifica(@Valid @RequestBody FuenteCientificaRequestDTO fuenteCientificaRequestDTO){
        FuenteCientificaResponseDTO fuenteCreada = fuenteCientificaService.CreraNuevaFuenteCientifica(fuenteCientificaRequestDTO);
        return new ResponseEntity<>(fuenteCreada, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<FuenteCientificaResponseDTO>> ObtenerTodasLasFuentes (){
        List<FuenteCientificaResponseDTO> fuentes = fuenteCientificaService.ObtenerTodasLasFuentes();
        return ResponseEntity.ok(fuentes);
    }


}
