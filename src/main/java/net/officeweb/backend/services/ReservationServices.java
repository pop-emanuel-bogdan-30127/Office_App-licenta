package net.officeweb.backend.services;

import lombok.extern.slf4j.Slf4j;
import net.officeweb.backend.entities.OfficeEntity;
import net.officeweb.backend.entities.Reservation;
import net.officeweb.backend.exceptions.ResourceNotFoundException;
import net.officeweb.backend.repositories.OfficeRepository;
import net.officeweb.backend.repositories.ReservationRepository;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Slf4j
@Service
public class ReservationServices {
    private final OfficeRepository officeRepository;

    private final ReservationRepository reservationRepository;

    public ReservationServices(ReservationRepository reservationRepository,
                               OfficeRepository officeRepository) {
        this.reservationRepository = reservationRepository;
        this.officeRepository = officeRepository;
    }

    public List<Reservation> getAllReservations(){
        return reservationRepository.findAll();
    }

    public Reservation getReservation(Long id){
        //validateReservationExistence(id);
        return reservationRepository.findById(id).get();
    }

    public List<Reservation> getByOfficeId(Long id){
        return reservationRepository.findByOfficeId(id);
    }

    public String saveReservation(Reservation reservations) {

        //boolean to determine if there is already a pre-existing reservation that overlaps with the users
        if (reservationOverlaps(reservations)) {
            return "overlap";
            }
            //determine if the dates are out of the Inventory's bounds  //TODO: getById is deprecated
        if (dateIsBefore(officeRepository.getById(reservations.getOffice().getId()).getAvailableFrom(), reservations.getCheckIn()) && dateIsBefore(reservations.getCheckOut(), officeRepository.getById(reservations.getOffice().getId()).getAvailableTo())) {
            //reservations.setPayment(Duration.between(reservations.getCheckIn(), reservations.getCheckOut()).toDays()*);
            reservationRepository.save(reservations);
             return "success";
        } else {
            //Throw error if the Inventory ID does not exist
            return "invalid";
            }
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }

    public boolean dateIsBefore(String date1, String date2) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"); //DD MM YYYY hh:mm:ss
        try {
            return simpleDateFormat.parse(date1).before(simpleDateFormat.parse(date2));
        } catch (ParseException e) {
            throw new RuntimeException("The date couldn't be converted in isBefore");
        }
    }

    public boolean reservationOverlaps(Reservation reservations) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        return reservationRepository.findAll().stream().anyMatch(dataBaseRes -> {
            if (dataBaseRes.getOffice().getId() == reservations.getOffice().getId()) {
                try {
                    int checkInBeforeDbCheckOut = sdf.parse(reservations.getCheckIn()).compareTo(sdf.parse(dataBaseRes.getCheckOut()));
                    int checkOutBeforeDbCheckIn = sdf.parse(reservations.getCheckOut()).compareTo(sdf.parse(dataBaseRes.getCheckIn()));
                    log.debug("check in int " + checkInBeforeDbCheckOut);
                    log.debug("check out int " + checkOutBeforeDbCheckIn);
                    if (checkInBeforeDbCheckOut == 0 || checkOutBeforeDbCheckIn == 0) {
                        return true;
                    } else {
                        return checkInBeforeDbCheckOut != checkOutBeforeDbCheckIn;
                    }
                } catch (ParseException e) {
                    throw new RuntimeException("The date couldn't be converted in overlap");
                }
            } else {
                return false;
            }
        });
    }

    public void dateUpdate(Long reservationId, String checkIn, String checkOut) throws ParseException {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation with id :" + reservationId + " does not exist. "));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        reservation.setCheckIn(sdf.format(checkIn));
        reservation.setCheckOut(sdf.format(checkOut));
        reservationRepository.save(reservation);
    }

    public boolean changeStatus(Long reservationId, boolean status){
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation with id :" + reservationId + " does not exist."));
        reservation.setStatus(status);
         reservationRepository.save(reservation);
         return status;
    }

    public Long officeIdByReservationId(Long reservationId){
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not exist with id :" + reservationId));
        return reservation.getOffice().getId();
    }
}
