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

import co.manuelerazo.tesis.dtos.categoria.CategoriaResponseDTO;
import co.manuelerazo.tesis.dtos.productos.ProductoRequestDTO;
import co.manuelerazo.tesis.dtos.productos.ProductoResponseDTO;
import co.manuelerazo.tesis.services.ProductoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {
    private final ProductoService productoService;
    public ProductoController(ProductoService productoService){
        this.productoService = productoService;
    }


    //1. para crear un nuevo producto de tipo post
    @PostMapping
    public ResponseEntity<ProductoResponseDTO>CrearNuevoProducto(@Valid @RequestBody ProductoRequestDTO productoRequestDTO){
        ProductoResponseDTO nuevoProducto = productoService.CrearProducto(productoRequestDTO);
        return new ResponseEntity<>(nuevoProducto, HttpStatus.CREATED);
    }
    //2. para leer todos los productos que existen en la base de datos
    @GetMapping
    public ResponseEntity<List<ProductoResponseDTO>> ObtenerTodosLosProductos(){
        List<ProductoResponseDTO> productos = productoService.ObtenerTodosLosProductos();
        return ResponseEntity.ok(productos);
    }

    //3. para buscar con el id
    //el {id} en la url es una variable de ruta
    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> ObtenerProductoId (@PathVariable Integer id){
        //extrameos el valor de {id} de la url
        ProductoResponseDTO producto = productoService.ObtenerProductoId(id);
        return ResponseEntity.ok(producto);
    }

    //para buscar con nombre
    //4.  el {nombre} en la url se convierte en una variable de ruta
    @GetMapping("/buscar/{nombre}")
    public ResponseEntity<ProductoResponseDTO> ObtenerProducoPorNombre(@PathVariable String nombre){
        //extraemos el nombre {nombre} de la url
        ProductoResponseDTO producto = productoService.BuscarProductoPorNombre(nombre);
        return ResponseEntity.ok(producto);
    }

    //5. obtener poroducto por categoria
    @GetMapping("/categoria/{idCategoria}")
    public ResponseEntity<List<ProductoResponseDTO>> ObtenerProductoPorCategoria(@PathVariable Integer idCategoria){
        List<ProductoResponseDTO> productos = productoService.ObtenerProductoPorCategoria(idCategoria);
        return ResponseEntity.ok(productos);
    }

    //6. para actualizar
    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> ActualizarProducto (@PathVariable Integer id, @Valid @RequestBody ProductoRequestDTO productoRequestDTO){
        ProductoResponseDTO productoActualizado = productoService.ActualizarProducto(id, productoRequestDTO);
        return ResponseEntity.ok(productoActualizado);
    }

    //7. para eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> EliminarProducto(@PathVariable Integer id){
        productoService.EliminarProducto(id);
        return ResponseEntity.noContent().build();
    }

    //8. asignar categoria a producto
    @PostMapping("/{idProducto}/categoria/{idCategoria}")
    public ResponseEntity<Void> AsignarCategoriaAProducto(@PathVariable Integer idProducto, @PathVariable Integer idCategoria){
        productoService.AsignarCategoriaAProducto(idProducto, idCategoria);

        return ResponseEntity.ok().build();
    }

    //9. quitar categoria de un producto
    @DeleteMapping("/{idProducto}/categoria/{idCategoria}")
    public ResponseEntity<Void> QuitarCategoriaDeProducto(@PathVariable Integer idProducto, @PathVariable Integer idCategoria){
        productoService.QuitarCategoriaDeProducto(idProducto, idCategoria);

        return ResponseEntity.noContent().build();
    }

    //10. listar categorias de un producto
    @GetMapping("/{idProducto}/categorias")
    public ResponseEntity<List<CategoriaResponseDTO>> ObtenerCategoriasDeProducto(@PathVariable Integer idProducto){
        return ResponseEntity.ok(
                   productoService.ObtenerCategoriasDeProducto(idProducto)
        );
    }
}
