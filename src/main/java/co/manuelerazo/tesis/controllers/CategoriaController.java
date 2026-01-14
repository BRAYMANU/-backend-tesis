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
import co.manuelerazo.tesis.dtos.Publicacion.PublicacionResponseDTO;
import co.manuelerazo.tesis.dtos.categoria.CategoriaRequestDTO;
import co.manuelerazo.tesis.dtos.categoria.CategoriaResponseDTO;
import co.manuelerazo.tesis.services.CategoriaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {
    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService){
        this.categoriaService = categoriaService;
    }

    //1. crear categoria
    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> CrearCategoria(@Valid @RequestBody CategoriaRequestDTO dto){
        CategoriaResponseDTO categoriaCreada = categoriaService.CrearCategoria(dto);
        return new ResponseEntity<>(categoriaCreada, HttpStatus.CREATED);
    }

    //2. obtener todas las categorias
    @GetMapping
    public ResponseEntity<List<CategoriaResponseDTO>> ObtenerTodasLasCategorias(){
        List<CategoriaResponseDTO> categorias = categoriaService.ObtenerTodasLasCategorias();
        return ResponseEntity.ok(categorias);
        
    }

    //3. obtener categoria por id
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> ObtenerCategoriaPorId(@PathVariable Integer id){
        CategoriaResponseDTO categoria = categoriaService.ObtenerCategoriaPorId(id);
        return ResponseEntity.ok(categoria);
    }

    //4.Actualizar categoria
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> ActualizarCategoria (@PathVariable Integer id, @Valid @RequestBody CategoriaRequestDTO dto){
        CategoriaResponseDTO categoriaActualizada = categoriaService.ActualizarCateoria(id, dto);
        return ResponseEntity.ok(categoriaActualizada);

        //o tambien asi:return ResponseEntity.ok(
               // categoriaService.ActualizarCateoria(id, dto)
    }

    //5.obtener publicaciones por categoria
    @GetMapping("/{id}/publicaciones")
    public ResponseEntity<List<PublicacionResponseDTO>> ObtenerPublicacionesPorCategoria(@PathVariable Integer id){
        List<PublicacionResponseDTO> publicaciones = categoriaService.ObtenerPublicacionesPorCategoria(id);
        return ResponseEntity.ok(publicaciones);
    }

    //6. eliminar categoria
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> EliminarCategoria(@PathVariable Integer id){
        categoriaService.EliminarCategoria(id);
        return ResponseEntity.noContent().build();
    }    
}
