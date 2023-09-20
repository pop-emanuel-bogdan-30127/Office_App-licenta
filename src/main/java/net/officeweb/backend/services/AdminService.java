package net.officeweb.backend.services;

import net.officeweb.backend.entities.AdminAccount;
import net.officeweb.backend.exceptions.ResourceNotFoundException;
import net.officeweb.backend.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {
    
    @Autowired
    private AdminRepository adminRepository;

    public List<AdminAccount> findAllAccounts() {

        return (List<AdminAccount>) adminRepository.findAll();
    }
    public Optional<AdminAccount> findById(Long id) {

        return adminRepository.findById(id);
    }
    public Optional<AdminAccount> findByUsername(String username) {
        return Optional.ofNullable(adminRepository.findAdminAccountByUsername(username));

    }
    public AdminAccount saveAccount(AdminAccount AdminAccount) {

        return adminRepository.save(AdminAccount);
    }
    public AdminAccount updateAccount(Long id, AdminAccount AdminAccount) {

        AdminAccount account = adminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account with id:" + id + "does not exist."));

        account.setUsername(AdminAccount.getUsername());
        account.setPassword(AdminAccount.getPassword());

        AdminAccount updatedAccount = adminRepository.save(account);
        return updatedAccount;
    }
    public void deleteAccount(Long id) {
        adminRepository.deleteById(id);

    }

    public AdminAccount adminLogin(String username, String password) {
        AdminAccount admin = adminRepository.findAdminAccountByUsernameAndPassword(username, password);
        return admin;
    }
}
