package net.officeweb.backend.controllers;

import net.officeweb.backend.entities.AdminAccount;
import net.officeweb.backend.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController (AdminService adminService){
        this.adminService = adminService;
    }

    @PostMapping
    public void create(@RequestBody AdminAccount adminAccount)throws IOException {
        adminService.saveAccount(adminAccount);
    }

    @GetMapping
    public List<AdminAccount> getAllAdmins(){
        return adminService.findAllAccounts();
    }


    @GetMapping(value = "{username}")
    public Optional<AdminAccount> getAdminByUsername(@PathVariable String username){
        return adminService.findByUsername(username);
    }



    @DeleteMapping("{id}")
    public void deleteAdmin(@PathVariable("id") Long id){
        adminService.deleteAccount(id);
    }

}
