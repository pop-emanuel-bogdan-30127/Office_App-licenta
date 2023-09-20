package net.officeweb.backend.entities;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "customerAccounts")
public class CustomerAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long id;

    @Column(name = "username")
    private String username;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.PERSIST, mappedBy = "customerAccount")
    private List<OfficeEntity> office;

    @Column(updatable = false, nullable = false)
    @CreationTimestamp
    private Date enrollDate;

    public CustomerAccount(Long id, String username, String email, String password, List<OfficeEntity> office, Date enrollDate) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
//        this.office = office;
        this.enrollDate = enrollDate;
    }

    public CustomerAccount() {
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    public List<OfficeEntity> getOffice() {
//        return office;
//    }
//
//    public void setOffice(List<OfficeEntity> office) {
//        this.office = office;
//    }

    public Date getEnrollDate() {
        return enrollDate;
    }


}

