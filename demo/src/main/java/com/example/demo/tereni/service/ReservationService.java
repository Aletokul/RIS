package com.example.demo.tereni.service;

import com.example.demo.tereni.model.*;
import com.example.demo.tereni.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.*;
import java.util.List;

@Service
public class ReservationService {
  private final ReservationRepository reservations;
  private final FieldRepository fields;
  private final UserRepository users;

  public ReservationService(ReservationRepository reservations, FieldRepository fields, UserRepository users) {
    this.reservations = reservations;
    this.fields = fields;
    this.users = users;
  }

  /** Moje rezervacije (korisnik) */
  public List<Reservation> myReservations(String username) {
    return reservations.findAllByUser_UsernameOrderByDateDescStartTimeDesc(username);
  }
  


public List<Reservation> pendingReservations() {
    return reservations.findByStatusOrderByCreatedAtDesc(Reservation.Status.PENDING);
}

@Transactional
public void adminApprove(Long id) {
    Reservation r = reservations.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));
    r.setStatus(Reservation.Status.CONFIRMED);
    reservations.save(r);
}

@Transactional
public void adminReject(Long id) {
    Reservation r = reservations.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));
    r.setStatus(Reservation.Status.CANCELLED);
    reservations.save(r);
}

  /** Kreiranje rezervacije (korisnik) */
  @Transactional
  public Reservation create(String username, Long fieldId,
                            LocalDate date, LocalTime start, LocalTime end,
                            String notes) {

    User user  = users.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("User not found"));
    Field field = fields.findById(fieldId).orElseThrow(() -> new IllegalArgumentException("Field not found"));

    if (!start.isBefore(end)) {
      throw new IllegalArgumentException("Start must be before end");
    }

    // 2) Preklapanje (ignoriše CANCELLED)
    if (reservations.existsOverlapping(field, date, start, end)) {
      throw new IllegalArgumentException("Time slot overlaps with an existing reservation");
    }

    // 3) Obračun cene
    long minutes = java.time.Duration.between(start, end).toMinutes();
    BigDecimal hours = BigDecimal.valueOf(minutes).divide(BigDecimal.valueOf(60), 2, RoundingMode.HALF_UP);
    BigDecimal total = hours.multiply(field.getPricePerHour()).setScale(2, RoundingMode.HALF_UP);

    // 4) Snimi kao PENDING
    Reservation r = new Reservation();
    r.setUser(user);
    r.setField(field);
    r.setDate(date);
    r.setStartTime(start);
    r.setEndTime(end);
    r.setTotalPrice(total);
    r.setNotes(notes);
    r.setStatus(Reservation.Status.PENDING); // eksplicitno
    return reservations.save(r);
  }
  
  public void cancel(Long id) {
	    Reservation r = reservations.findById(id).orElseThrow();
	    r.setStatus(Reservation.Status.CANCELLED);
	    reservations.save(r);
	}

}
