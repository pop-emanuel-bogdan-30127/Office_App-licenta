package net.officeweb.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long id;

    @Column(updatable = false, nullable = false)
    @CreationTimestamp
    private Date creation;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "b_id", nullable = false)  // office_id
    @JsonIgnore
    private OfficeEntity office;

    @Column(name = "customer")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "tel")
    private String telephone;

    @Column(name = "check_in")
    private String checkIn;

    @Column(name = "check_out")
    private String checkOut;

//    @Column(name = "payment")
//    private Integer payment;

    @Column
    private boolean status;



//    @Column(name = "payment_status")
//    private boolean payment;


}
