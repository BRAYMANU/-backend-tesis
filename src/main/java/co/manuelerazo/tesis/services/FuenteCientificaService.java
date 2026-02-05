package co.manuelerazo.tesis.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import co.manuelerazo.tesis.dtos.fuenteCientifica.FuenteCientificaRequestDTO;
import co.manuelerazo.tesis.dtos.fuenteCientifica.FuenteCientificaResponseDTO;
import co.manuelerazo.tesis.entitis.FuenteCientifica;
import co.manuelerazo.tesis.entitis.Producto;
import co.manuelerazo.tesis.exceptions.ResourceNotFoundException;
import co.manuelerazo.tesis.repositories.FuenteCientificaRepository;
import co.manuelerazo.tesis.repositories.ProductoRepository;


@Service
public class FuenteCientificaService {
    private final FuenteCientificaRepository fuenteCientificaRepository;
    private final ProductoRepository productoRepository;

    public FuenteCientificaService (FuenteCientificaRepository fuenteCientificaRepository, ProductoRepository productoRepository){
        this.fuenteCientificaRepository = fuenteCientificaRepository;
        this.productoRepository =productoRepository;
    }

    //1. metodo para crear nueva fuente cientifica 
    public FuenteCientificaResponseDTO CrearNuevaFuenteCientifica (FuenteCientificaRequestDTO fuenteCientificaRequestDTO){
        FuenteCientifica nuevaFuente = new FuenteCientifica();
        nuevaFuente.setTitulo(fuenteCientificaRequestDTO.getTitulo());
        nuevaFuente.setEnlace(fuenteCientificaRequestDTO.getEnlace());

        FuenteCientifica fuenteGuardada = fuenteCientificaRepository.save(nuevaFuente);
        return ConvertirADTOBasico(fuenteGuardada);
    }

    //2. metodo para obtener todas las fuentes cientificas
    public List<FuenteCientificaResponseDTO> ObtenerTodasLasFuentes (){
        List<FuenteCientifica> fuentes = fuenteCientificaRepository.findAll();
        return fuentes.stream()
                .map(this::ConvertirADTOBasico)
                .collect(Collectors.toList());
    }

    //3. metodo para obtener fuente cientifica por id
    public FuenteCientificaResponseDTO ObtenerFuenteCientificaPorId(Integer id){
        FuenteCientifica fuente = fuenteCientificaRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Fuente cientifica no encontrada con el id: "+id));

        return ConvertirADTOBasico(fuente);
    }

    //4.metodo para actulizar fuente cientifica
    public FuenteCientificaResponseDTO ActualizarFuenteCientifica(Integer id, FuenteCientificaRequestDTO fuenteCientificaRequestDTO){
        FuenteCientifica fuenteCientifica = fuenteCientificaRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Fuente cientifica no encontrada con el id: "+id));
        
        fuenteCientifica.setTitulo(fuenteCientificaRequestDTO.getTitulo()); 
        fuenteCientifica.setEnlace(fuenteCientificaRequestDTO.getEnlace());  
        
        FuenteCientifica fuenteActualizada = fuenteCientificaRepository.save(fuenteCientifica);
        return ConvertirADTOBasico(fuenteActualizada);
    }

    //5. metodo para eliminar fuente cientifica
    public void EliminarFuenteCientifica (Integer id){
        FuenteCientifica fuenteCientifica = fuenteCientificaRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Fuente cientifica no encontrada con el id: "+id));

        //rompemos la relacion
        for(Producto producto : fuenteCientifica.getProductos()){
            producto.getFuenteCientificas().remove(fuenteCientifica);
        } 

        fuenteCientifica.getProductos().clear();

        //eliminamos la fuente 
        fuenteCientificaRepository.delete(fuenteCientifica);
    }

    //6. obtener fuentes cientificas por producto
    public List<FuenteCientificaResponseDTO> ObteneFuentesPorProducto(Integer productoId){

        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(()->new ResourceNotFoundException("Producto no encontrado con el id: "+productoId));

        return producto.getFuenteCientificas()
                .stream()
                .map(this::ConvertirADTOBasico)
                .collect(Collectors.toList());
    }

    //metodo auxiliar para convertir entidad a DTO sin entidad relacionada
    private FuenteCientificaResponseDTO ConvertirADTOBasico(FuenteCientifica fuenteCientifica){
        FuenteCientificaResponseDTO dto = new FuenteCientificaResponseDTO();

        dto.setId(fuenteCientifica.getId());
        dto.setTitulo((fuenteCientifica.getTitulo()));
        dto.setEnlace(fuenteCientifica.getEnlace());

        if(!fuenteCientifica.getProductos().isEmpty()){
            dto.setProductoNombre(fuenteCientifica.getProductos().iterator().next().getNombre());
        }

        return dto;
    }
}

    

