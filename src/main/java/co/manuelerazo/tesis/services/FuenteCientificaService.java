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

    //metodo para crear nueva fuente cientifica 
    public FuenteCientificaResponseDTO CreraNuevaFuenteCientifica (FuenteCientificaRequestDTO fuenteCientificaRequestDTO){
        //1.buscar la entidad relacionada (producto)
        Producto producto = productoRepository.findById(fuenteCientificaRequestDTO.getProducto_id())
        .orElseThrow(()->new ResourceNotFoundException("tipo de producto no encontrado con el id "+ fuenteCientificaRequestDTO.getProducto_id()));
        
        //mapea el Dtoa a la entidad fuente cientifica
        FuenteCientifica nuevaFuenteCientifica = new FuenteCientifica();
        nuevaFuenteCientifica.setTitulo(fuenteCientificaRequestDTO.getTitulo());
        nuevaFuenteCientifica.setEnlace((fuenteCientificaRequestDTO.getEnlace()));

        //asigano la entedidad relacionada
        nuevaFuenteCientifica.setProducto(producto);

        //guardamos la nueva fuente cientifica 
        FuenteCientifica fuenteCientificaGuaradada = fuenteCientificaRepository.save(nuevaFuenteCientifica);

        //convertimos al DTO de respuesta y retornar
        return ConvertirADTO(fuenteCientificaGuaradada);
    }

    public List<FuenteCientificaResponseDTO> ObtenerTodasLasFuentes (){
        return fuenteCientificaRepository.findAll().stream()
                    .map(this::ConvertirADTO)
                    .collect(Collectors.toList());
    }


    private FuenteCientificaResponseDTO ConvertirADTO(FuenteCientifica fuenteCientifica){
        FuenteCientificaResponseDTO dto = new FuenteCientificaResponseDTO();

        dto.setId(fuenteCientifica.getId());
        dto.setTitulo((fuenteCientifica.getTitulo()));
        dto.setEnlace(fuenteCientifica.getEnlace());

        //asiganamos el nombre del producto a la entidad relacionada

        dto.setProductoNombre(fuenteCientifica.getProducto().getNombre());
        return dto;
    }
   

   }

    

