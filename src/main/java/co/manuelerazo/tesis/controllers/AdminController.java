package co.manuelerazo.tesis.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.manuelerazo.tesis.dtos.profesionalSalud.ProfesionalSaludResponseDTO;
import co.manuelerazo.tesis.services.AdminService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService){
        this.adminService = adminService;
    }
    
    //1. Ver profesionales pendientes de validación
    @GetMapping("/profesionales/pendientes")
    public ResponseEntity<List<ProfesionalSaludResponseDTO>> obtenerProfesionalesPendientes(){
        List<ProfesionalSaludResponseDTO> profesionalesPendientes = adminService.obtenerProfesionalesPendientes();
        return ResponseEntity.ok(profesionalesPendientes);
    }

    //2. aprobar profesional en salud
    @PutMapping("/profesionales/{id}/aprobar")
    public ResponseEntity<ProfesionalSaludResponseDTO> aprobarProfesional(@PathVariable Integer id){
        ProfesionalSaludResponseDTO profesionalAprobado = adminService.aprobarProfesional(id);
        return ResponseEntity.ok(profesionalAprobado);
    }




    
}

