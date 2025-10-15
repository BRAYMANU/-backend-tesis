package co.manuelerazo.tesis.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import co.manuelerazo.tesis.dtos.productos.ProductoRequestDTO;
import co.manuelerazo.tesis.dtos.productos.ProductoResponseDTO;
import co.manuelerazo.tesis.entitis.Producto;
import co.manuelerazo.tesis.exceptions.ResourceNotFoundException;
import co.manuelerazo.tesis.repositories.ProductoRepository;

@Service
public class ProductoService {
    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository){
        this.productoRepository = productoRepository;
    }
    //crear producto
    public ProductoResponseDTO CrearProducto(ProductoRequestDTO productoRequestDTO){
        Producto nuevoProducto = new Producto();
        nuevoProducto.setNombre(productoRequestDTO.getNombre());
        nuevoProducto.setDescripcion(productoRequestDTO.getDescripcion());
        nuevoProducto.setCategoria(productoRequestDTO.getCategoria());
        nuevoProducto.setAdvertencias(productoRequestDTO.getAdvertencias());

        Producto productoGuardado = productoRepository.save(nuevoProducto);
        return convertirA_DTO(productoGuardado);

    }
    //obtener todos los productos 
    public List<ProductoResponseDTO> ObtenerTodosLosProductos(){
        List<Producto> productos = productoRepository.findAll();

        return productos.stream()
                .map(this::convertirA_DTO)
                .collect(Collectors.toList());
    }

    //metodo para obtener producto por id
    public ProductoResponseDTO ObtenerProductoId(Integer id){
        //buscamos por id
        Producto producto = productoRepository.findById(id)
                        //si no lo encuntra mostramos la excepcion persinalizada
                        .orElseThrow(()->new ResourceNotFoundException("Producto no encontrado con el id: "+id));

        //si lo encuentra lo convertimos al dto y lo retornamos 
        return convertirA_DTO(producto);
    }

    //metodo para actualizar producto
    public ProductoResponseDTO ActualizarProducto (Integer id, ProductoRequestDTO productoRequestDTO){
        //buscamos por id
        Producto productoExistente = productoRepository.findById(id)
                        //si no lo encuntra mostramos la excepcion persinalizada
                        .orElseThrow(()->new ResourceNotFoundException("Producto no se encuentra con el id: "+id));

        //actualizamos
        productoExistente.setNombre(productoRequestDTO.getNombre());
        productoExistente.setDescripcion(productoRequestDTO.getDescripcion());
        productoExistente.setCategoria(productoRequestDTO.getCategoria());
        productoExistente.setCategoria(productoRequestDTO.getCategoria());
        
        //guardamos
        Producto productoActualizado = productoRepository.save(productoExistente);

        //convertimo al dto y retorno el resultado
        return convertirA_DTO(productoActualizado);
    } 

    //metodo para eliminar producto

    public void EliminarProducto(Integer id){
        //verificamos si el produco existe
        if(!productoRepository.existsById(id)){
            throw new ResourceNotFoundException("No se puede eliminar producto no encontrado: "+id);
        
        }
        //si lo encuentra lo eliminamos
        productoRepository.deleteById(id);
    }

    //metodo para buscar el producto por nombre
    public ProductoResponseDTO BuscarProductoPorNombre(String nombre) {
    Producto producto = productoRepository.findByNombre(nombre)
        .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con el nombre: " + nombre));

    return convertirA_DTO(producto);
}

    


    //metodo privado para los dtos
    private ProductoResponseDTO convertirA_DTO(Producto producto){
        ProductoResponseDTO dto = new ProductoResponseDTO();
        dto.setId(producto.getId());
        dto.setNombre(producto.getNombre());
        dto.setDescripcion(producto.getDescripcion());
        dto.setCategoria(producto.getCategoria());
        dto.setAdvertencias(producto.getAdvertencias());
        return dto;
    }
    
}
