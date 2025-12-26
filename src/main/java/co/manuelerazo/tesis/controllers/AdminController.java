package co.manuelerazo.tesis.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.manuelerazo.tesis.dtos.profesionalSalud.ProfesionalSaludResponseDTO;
import co.manuelerazo.tesis.services.AdminService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService){
        this.adminService = adminService
    }


    
}

