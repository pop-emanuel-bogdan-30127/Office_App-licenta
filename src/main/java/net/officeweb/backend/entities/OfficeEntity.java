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
import jakarta.persistence.Transient;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;

@Entity
@Table(name = "offices")
public class OfficeEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "b_id")
        private Long id;

        @Column(name = "title")
        private String title;

        @Column(name = "space_m2")
        private Float space;

        @Column(name = "price")
        private Float price;

        @Column(name = "floor")
        private Integer floor;

        @Column(name = "nr_parking_spots")
        private Integer parking;

        @Column(name = "construction_year")
        private Integer year;

        @Column(name = "address")
        private String address;

        @Column(name = "city")
        private String city;

        @Column(name = "telephone_nr")
        private String telephone;

        @Column(name = "description", columnDefinition = "text")
        private String description;

        @Column(updatable = false, nullable = false)
        @CreationTimestamp
        private Date creationDate;

        @Column(name = "images", nullable = true, length = 64)
        private String images;

        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "account_id", nullable = false)
//        @OnDelete(action = OnDeleteAction.CASCADE)
        @JsonIgnore
        private CustomerAccount customerAccount;

        @Column(name = "available_from")
        private String availableFrom;

        @Column(name = "available_to")
        private String availableTo;


        public OfficeEntity() {
            }

        public OfficeEntity(Long id, String title, Float space, Float price, Integer floor, Integer parking,
                            Integer year, String address, String city, String telephone, String description,
                            CustomerAccount customerAccount, Date creationDate, String images) {
            this.id = id;
            this.title = title;
            this.space = space;
            this.price = price;
            this.floor = floor;
            this.parking = parking;
            this.year = year;
            this.address = address;
            this.city = city;
            this.telephone = telephone;
            this.description = description;
            this.creationDate = creationDate;
            this.customerAccount = customerAccount;
            this.images = images;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Float getSpace() {
            return space;
        }

        public void setSpace(Float space) {
            this.space = space;
        }

        public Float getPrice() {
            return price;
        }

        public void setPrice(Float price) {
            this.price = price;
        }

        public Integer getFloor() {
            return floor;
        }

        public void setFloor(Integer floor) {
            this.floor = floor;
        }

        public Integer getParking() {
            return parking;
        }

        public void setParking(Integer parking) {
            this.parking = parking;
        }

        public Integer getYear() {
            return year;
        }

        public void setYear(Integer year) {
            this.year = year;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
        public String getCity() {
        return city;
    }

        public void setCity(String city) {
            this.city = city;
        }
        public String getTelephone() {
        return telephone;
    }

        public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

        public String getDescription() {
        return description;
    }

        public void setDescription(String description) {
        this.description = description;
    }

        public Date getCreationDate() { return creationDate;}

        public CustomerAccount getCustomerAccount() {
            return customerAccount;
        }

        public void setCustomerAccount(CustomerAccount customerAccount) {
            this.customerAccount = customerAccount;
        }

        public String getImages() {
            return images;
        }
        public void setImages(String images) {
            this.images = images;
        }

    public String getAvailableFrom() {
        return availableFrom;
    }

    public void setAvailableFrom(String availableFrom) {
        this.availableFrom = availableFrom;
    }

    public String getAvailableTo() {
        return availableTo;
    }

    public void setAvailableTo(String availableTo) {
        this.availableTo = availableTo;
    }

    @Transient
        public String getPhotosImagePath() {
            if (images == null || id == null) return null;
            return "/user-photos/" + id + "/" + images;
        }


}



