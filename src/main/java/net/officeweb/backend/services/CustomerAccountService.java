package net.officeweb.backend.services;

import net.officeweb.backend.entities.AdminAccount;
import net.officeweb.backend.entities.CustomerAccount;
import net.officeweb.backend.exceptions.ResourceNotFoundException;
import net.officeweb.backend.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerAccountService {

    @Autowired
    private final CustomerRepository customerRepository;

    public CustomerAccountService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<CustomerAccount> findAllAccounts() {

        return customerRepository.findAll();
    }
    public Optional<CustomerAccount> findById(Long id) {

        return customerRepository.findById(id);
    }
    public Optional<CustomerAccount> findByUsername(String username){
        return Optional.ofNullable(customerRepository.findCustomerAccountByUsername(username));
    }

    public Optional<CustomerAccount> findByEmail(String email){
        return Optional.ofNullable(customerRepository.findCustomerAccountByEmail(email));
    }
    public CustomerAccount saveAccount(CustomerAccount CustomerAccount) {

        return customerRepository.save(CustomerAccount);
    }
    public CustomerAccount updateAccount(Long id, CustomerAccount CustomerAccount) {

        CustomerAccount account = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account with id:" + id + "does not exist."));

        account.setEmail(CustomerAccount.getEmail());
        account.setUsername(CustomerAccount.getUsername());
        account.setPassword(CustomerAccount.getPassword());

        CustomerAccount updatedAccount = customerRepository.save(account);
        return updatedAccount;
    }
    public void deleteAccount(Long id) {
        customerRepository.deleteById(id);

    }
}
