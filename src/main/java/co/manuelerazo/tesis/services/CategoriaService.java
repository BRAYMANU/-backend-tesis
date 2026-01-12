package co.manuelerazo.tesis.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.manuelerazo.tesis.dtos.categoria.CategoriaRequestDTO;
import co.manuelerazo.tesis.dtos.categoria.CategoriaResponseDTO;
import co.manuelerazo.tesis.entitis.Categoria;
import co.manuelerazo.tesis.repositories.CategoriaRepository;
import co.manuelerazo.tesis.dtos.Publicacion.PublicacionResponseDTO;
import co.manuelerazo.tesis.entitis.Publicacion;
import co.manuelerazo.tesis.exceptions.ResourceNotFoundException;

@Service
public class CategoriaService {
    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository){
        this.categoriaRepository = categoriaRepository;
    }

    //1. crear categoria
    @Transactional
    public CategoriaResponseDTO CrearCategoria(CategoriaRequestDTO dto){
        if(categoriaRepository.existsByNombre(dto.getNombre())){
            throw new IllegalArgumentException("la categoria ya existe");
        }

        Categoria categoria = new Categoria();
        categoria.setNombre(dto.getNombre());
        categoria.setDescripcion(dto.getDescripcion());

        Categoria categoriaGuardada = categoriaRepository.save(categoria);
        return convertirA_DTO(categoriaGuardada);
        
        //o asi -> return convertirADTO(categoriaRepository.save(categoria));
    }

    //2. obtener todas las categorias
    public List<CategoriaResponseDTO> ObtenerTodasLasCategorias(){
        return categoriaRepository.findAll()
                .stream()
                .map(this::convertirA_DTO)
                .toList();
    }

    //3. obtener categoria por id
    public CategoriaResponseDTO ObtenerCategoriaPorId(Integer id){
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException( "Categoría no encontrada con id: " + id));
        
        return convertirA_DTO(categoria);
    }

    //4. actualizar ctegoria
    @Transactional
    public CategoriaResponseDTO ActualizarCateoria(Integer id, CategoriaRequestDTO dto){
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Categoría no encontrada con id: " + id));
      //¿El nombre viejo es IGUAL al nombre nuevo?Ese código SOLO detecta si el nombre cambió o no.
        if(categoriaRepository.existsByNombre(dto.getNombre()) && !categoria.getNombre().equals(dto.getNombre())){
            throw new IllegalArgumentException("Ya existe una categoría con ese nombre");
        }

        categoria.setNombre(dto.getNombre());
        categoria.setDescripcion(dto.getDescripcion());

        Categoria categoriaActualizada = categoriaRepository.save(categoria);
        return convertirA_DTO(categoriaActualizada);
    }

    //5. obtener publicacion por categoria
    public List<PublicacionResponseDTO> ObtenerPublicacionesPorCategoria(Integer idCategoria){
        Categoria categoria = categoriaRepository.findById(idCategoria)
                .orElseThrow(()-> new ResourceNotFoundException("Categoría no encontrada con id: " + idCategoria));
        
        return categoria.getPublicaciones()
                .stream()
                .map(this::convertirPublicacionADTO)
                .toList();
    }

    //6.eliminar categoria
    @Transactional
    public void EliminarCategoria(Integer id){

        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Categoría no encontrada con id: " + id));

       // Romper la relación con publicaciones (tabla intermedia)  
       categoria.getPublicaciones().clear();

       categoriaRepository.delete(categoria);  
    }

    //metodo privado para convertir publicacion a dto
    private PublicacionResponseDTO convertirPublicacionADTO(Publicacion p){
        PublicacionResponseDTO dto = new PublicacionResponseDTO();

        dto.setId(p.getId());
        dto.setTitulo(p.getTitulo());
        dto.setContenido(p.getContenido());
        dto.setFechaPublicacion(p.getFechaPublicacion());

        if(p.getProfesionalSalud() != null){
            dto.setIdProfesionalSalud(p.getProfesionalSalud().getId());
            dto.setNombreProfesional(p.getProfesionalSalud().getNombre());
        }
        return dto;
    }


    //metodo privado para convertir entidad a dto
    private CategoriaResponseDTO convertirA_DTO(Categoria categoria){
        CategoriaResponseDTO dto = new CategoriaResponseDTO();

        dto.setId(categoria.getId());
        dto.setNombre(categoria.getNombre());
        dto.setDescripcion(categoria.getDescripcion());

        return dto;    
    }
    
}
