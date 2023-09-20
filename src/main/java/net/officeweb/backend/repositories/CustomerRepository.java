package net.officeweb.backend.repositories;

import net.officeweb.backend.entities.AdminAccount;
import net.officeweb.backend.entities.CustomerAccount;
import net.officeweb.backend.entities.OfficeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CustomerRepository extends JpaRepository<CustomerAccount, Long> {
    CustomerAccount findCustomerAccountByUsername(String username);
    CustomerAccount findCustomerAccountByEmail(String email);
}
