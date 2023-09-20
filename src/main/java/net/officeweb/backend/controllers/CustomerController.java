package net.officeweb.backend.controllers;


import net.officeweb.backend.entities.CustomerAccount;
import net.officeweb.backend.entities.OfficeEntity;
import net.officeweb.backend.services.CustomerAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {
    
    @Autowired
    private final CustomerAccountService customerService;

    @Autowired
    public CustomerController(CustomerAccountService customerAccountService) {
        this.customerService = customerAccountService;
    }

    @PostMapping
    public void create(@RequestBody CustomerAccount customerAccount)throws IOException {
        customerService.saveAccount(customerAccount);
    }

    @GetMapping
    public List<CustomerAccount> getAllCustomers(){
        return customerService.findAllAccounts();
    }


    @GetMapping(value = "/username/{username}")
    public Optional<CustomerAccount> getCustomerByUsername(@PathVariable String username){
        return customerService.findByUsername(username);
    }

    @GetMapping(value = "/email/{email}")
    public Optional<CustomerAccount> getCustomerByEmail(@PathVariable String email){
        return customerService.findByEmail(email);
    }

    @GetMapping(value = "/getById-{id}")
    public Optional<CustomerAccount> getCustomerById(@PathVariable Long id){
        return customerService.findById(id);
    }

    @PutMapping("/{id}")
    public CustomerAccount updateCustomerAccount(@PathVariable Long id, @RequestBody CustomerAccount customerAccount){
        return customerService.updateAccount(id, customerAccount);
    }

    @DeleteMapping("{id}")
    public void deleteCustomer(@PathVariable("id") Long id){
        customerService.deleteAccount(id);
    }


}
