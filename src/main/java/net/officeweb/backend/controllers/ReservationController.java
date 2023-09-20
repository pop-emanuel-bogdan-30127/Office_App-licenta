package net.officeweb.backend.controllers;

import net.officeweb.backend.entities.Reservation;
import net.officeweb.backend.exceptions.ResourceNotFoundException;
import net.officeweb.backend.services.OfficeService;
import net.officeweb.backend.services.ReservationServices;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/reservation")
public class ReservationController {
    private final ReservationServices reservationServices;
    private final OfficeService officeService;

    public ReservationController(ReservationServices reservationServices, OfficeService officeService) {
        this.reservationServices = reservationServices;
        this.officeService = officeService;
    }

    @GetMapping
    public List<Reservation> getAllReservations(){
        return reservationServices.getAllReservations();
    }

    @GetMapping("/{id}")
    public Reservation getReservationById(@PathVariable Long id){
        return reservationServices.getReservation(id);
    }

    @GetMapping("/{officeId}/office")
    public List<Reservation> findReservationByOfficeId(@PathVariable("officeId") Long officeId){
        List<Reservation> reservations = reservationServices.getByOfficeId(officeId);
        return reservations;
    }

    @GetMapping("/{reservationId}/officeByReservation")
    public Long getOfficeId(@PathVariable("reservationId") Long reservationId){
        return reservationServices.officeIdByReservationId(reservationId);
    }

    @PostMapping("/{officeId}/reservation")
    public String createReservation(@PathVariable("officeId") Long officeId, @RequestBody Reservation reservation){

        String newReservation = officeService.findById(officeId).map(office -> {
            reservation.setOffice(office);
            return reservationServices.saveReservation(reservation);
        }).orElseThrow(() -> new ResourceNotFoundException("Office not found for id = " + officeId));
        return newReservation;
    }

    @DeleteMapping("/{reservationId}")
        public void deleteReservation(@PathVariable("reservationId") Long reservationId){
            reservationServices.deleteReservation(reservationId);
        }

        @PatchMapping("/{reservationId}/modify")
    public void updateDate(@PathVariable("reservationId") Long reservationId, @RequestParam String checkIn, @RequestParam String checkOut) throws ParseException {
        reservationServices.dateUpdate(reservationId, checkIn, checkOut);
        }

        @PatchMapping("/{reservationId}/status")
    public boolean statusUpdate(@PathVariable("reservationId") Long reservationId, @RequestParam boolean status){
             return   reservationServices.changeStatus(reservationId, status);
        }
}
