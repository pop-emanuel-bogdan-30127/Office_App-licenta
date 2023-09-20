package net.officeweb.backend.repositories;

import net.officeweb.backend.entities.AdminAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends CrudRepository<AdminAccount, Long> {
    AdminAccount findAdminAccountByUsernameAndPassword(String username, String password);

    AdminAccount findAdminAccountByUsername(String username);
}
