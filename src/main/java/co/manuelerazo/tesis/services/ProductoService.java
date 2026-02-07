package co.manuelerazo.tesis.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import co.manuelerazo.tesis.dtos.categoria.CategoriaResponseDTO;
import co.manuelerazo.tesis.dtos.productos.ProductoRequestDTO;
import co.manuelerazo.tesis.dtos.productos.ProductoResponseDTO;
import co.manuelerazo.tesis.entitis.Categoria;
import co.manuelerazo.tesis.entitis.Producto;
import co.manuelerazo.tesis.exceptions.ResourceNotFoundException;
import co.manuelerazo.tesis.repositories.ProductoRepository;
import co.manuelerazo.tesis.repositories.CategoriaRepository;
import co.manuelerazo.tesis.entitis.FuenteCientifica;
import co.manuelerazo.tesis.repositories.FuenteCientificaRepository;
import co.manuelerazo.tesis.dtos.fuenteCientifica.FuenteCientificaResponseDTO;

@Service
public class ProductoService {
    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final FuenteCientificaRepository fuenteCientificaRepository;

    public ProductoService(ProductoRepository productoRepository, CategoriaRepository categoriaRepository, FuenteCientificaRepository fuenteCientificaRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
        this.fuenteCientificaRepository = fuenteCientificaRepository;
    }
    //1. crear producto
    public ProductoResponseDTO CrearProducto(ProductoRequestDTO productoRequestDTO){
        Producto nuevoProducto = new Producto();
        nuevoProducto.setNombre(productoRequestDTO.getNombre());
        nuevoProducto.setDescripcion(productoRequestDTO.getDescripcion());
        nuevoProducto.setAdvertencias(productoRequestDTO.getAdvertencias());

        Producto productoGuardado = productoRepository.save(nuevoProducto);
        return convertirA_DTO(productoGuardado);

    }
    //2. obtener todos los productos 
    public List<ProductoResponseDTO> ObtenerTodosLosProductos(){
        List<Producto> productos = productoRepository.findAll();

        return productos.stream()
                .map(this::convertirA_DTO)
                .collect(Collectors.toList());
    }

    //3. metodo para obtener producto por id
    public ProductoResponseDTO ObtenerProductoId(Integer id){
        //buscamos por id
        Producto producto = productoRepository.findById(id)
                        //si no lo encuntra mostramos la excepcion persinalizada
                        .orElseThrow(()->new ResourceNotFoundException("Producto no encontrado con el id: "+id));

        //si lo encuentra lo convertimos al dto y lo retornamos 
        return convertirA_DTO(producto);
    }

    //4. metodo para buscar el producto por nombre
    public ProductoResponseDTO BuscarProductoPorNombre(String nombre) {
    Producto producto = productoRepository.findByNombre(nombre)
        .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con el nombre: " + nombre));

    return convertirA_DTO(producto);
    }
    
    //5. obtener productos por categoria
    public List<ProductoResponseDTO> ObtenerProductoPorCategoria(Integer idCategoria){
        Categoria categoria = categoriaRepository.findById(idCategoria)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con id: " + idCategoria));
        
        return categoria.getProductos()
                .stream()
                .map(this::convertirA_DTO)
                .toList();
    }

    //6. metodo para actualizar producto
    public ProductoResponseDTO ActualizarProducto (Integer id, ProductoRequestDTO productoRequestDTO){
        //buscamos por id
        Producto productoExistente = productoRepository.findById(id)
                        //si no lo encuntra mostramos la excepcion persinalizada
                        .orElseThrow(()->new ResourceNotFoundException("Producto no se encuentra con el id: "+id));

        //actualizamos
        productoExistente.setNombre(productoRequestDTO.getNombre());
        productoExistente.setDescripcion(productoRequestDTO.getDescripcion());
        
        
        //guardamos
        Producto productoActualizado = productoRepository.save(productoExistente);

        //convertimo al dto y retorno el resultado
        return convertirA_DTO(productoActualizado);
    } 

    //7. metodo para eliminar producto 
    public void EliminarProducto(Integer id){
        //verificamos si el produco existe
        if(!productoRepository.existsById(id)){
            throw new ResourceNotFoundException("No se puede eliminar producto no encontrado: "+id);
        
        }
        //si lo encuentra lo eliminamos
        productoRepository.deleteById(id);
    }

    //8. asignar categoria a un producto
    public void AsignarCategoriaAProducto(Integer idProducto, Integer idCategoria){
        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + idProducto));
        
        Categoria categoria = categoriaRepository.findById(idCategoria)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con id: " + idCategoria));
        
        //evitar duplicados
        if(producto.getCategorias().contains(categoria)){
            return; // La categoría ya está asignada al producto
        }

        //sincronizar ambas partes de la relación
        producto.getCategorias().add(categoria);
        categoria.getProductos().add(producto);

        productoRepository.save(producto);
    }

    //9. quuitar categoria de un producto
    public void QuitarCategoriaDeProducto(Integer idProducto, Integer idCategoria){
        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + idProducto));
        
        Categoria categoria = categoriaRepository.findById(idCategoria)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con id: " + idCategoria));
        
        //verificar si la categoria esta asignada al producto
        if(!producto.getCategorias().contains(categoria)){
            return; // La categoría no está asignada al producto
        }

        //sincronizar ambas partes de la relación
        producto.getCategorias().remove(categoria);
        categoria.getProductos().remove(producto);

        productoRepository.save(producto);
    }

    //10. metodo para listar categorias de un producto
    public List<CategoriaResponseDTO> ObtenerCategoriasDeProducto(Integer idProducto){
        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + idProducto));
        
        return producto.getCategorias()
                .stream()
                .map(this::convertirCategoriaADTO)
                .toList();
    }

    //11. asignar fuente cientifica a un producto
    public void AsignarFuenteCientificaAProducto(Integer idProducto, Integer idFuente){
        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + idProducto));
        
        FuenteCientifica fuente = fuenteCientificaRepository.findById(idFuente)
                .orElseThrow(() -> new ResourceNotFoundException("Fuente Científica no encontrada con id: " + idFuente));
        
        //evitar duplicados
        if(producto.getFuenteCientificas().contains(fuente)){
            return; // La fuente científica ya está asignada al producto
        }

        //sincronizar ambas partes de la relación
        producto.getFuenteCientificas().add(fuente);
        fuente.getProductos().add(producto);

        productoRepository.save(producto);
    }

    //12. quitar fuente cientifica de un producto
    public void QuitarFuenteCientificaDeProducto(Integer idProducto, Integer idFuente){
        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + idProducto));
        
        FuenteCientifica fuente = fuenteCientificaRepository.findById(idFuente)
                .orElseThrow(() -> new ResourceNotFoundException("Fuente Científica no encontrada con id: " + idFuente));
        
        //verificar si la fuente esta asignada al producto
        if(!producto.getFuenteCientificas().contains(fuente)){
            return; // La fuente científica no está asignada al producto
        }

        //sincronizar ambas partes de la relación
        producto.getFuenteCientificas().remove(fuente);
        fuente.getProductos().remove(producto);

        productoRepository.save(producto);
    }

    //13. metodo para listar fuentes cientificas de un producto
    public List<FuenteCientificaResponseDTO> ObtenerFuentesDeProducto(Integer idProducto){
        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + idProducto));
        
        return producto.getFuenteCientificas()
                .stream()
                .map(this::convertirFuenteADTO)
                .toList();
    }

    //metodo privado para convertir fuente cientifica a dto
    private FuenteCientificaResponseDTO convertirFuenteADTO(FuenteCientifica fuenteCientifica){
        FuenteCientificaResponseDTO dto = new FuenteCientificaResponseDTO();
        dto.setId(fuenteCientifica.getId());
        dto.setTitulo(fuenteCientifica.getTitulo());
        dto.setEnlace(fuenteCientifica.getEnlace());

        return dto;
    }

    //metodo privado para convertir categoria a dto
    private CategoriaResponseDTO convertirCategoriaADTO(Categoria categoria){
        CategoriaResponseDTO dto = new CategoriaResponseDTO();
        dto.setId(categoria.getId());
        dto.setNombre(categoria.getNombre());
        dto.setDescripcion(categoria.getDescripcion());
        
        return dto;
    }

    //metodo privado para los dtos
    private ProductoResponseDTO convertirA_DTO(Producto producto){
        ProductoResponseDTO dto = new ProductoResponseDTO();
        dto.setId(producto.getId());
        dto.setNombre(producto.getNombre());
        dto.setDescripcion(producto.getDescripcion());
        dto.setAdvertencias(producto.getAdvertencias());
        return dto;
    }
    
}
