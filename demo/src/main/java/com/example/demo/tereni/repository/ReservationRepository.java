package com.example.demo.tereni.repository;

import com.example.demo.tereni.model.Field;
import com.example.demo.tereni.model.Reservation;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findAllByUser_UsernameOrderByDateDescStartTimeDesc(String username);

    List<Reservation> findByStatusOrderByCreatedAtDesc(Reservation.Status status);

    @Query("""
      SELECT COUNT(r) > 0 FROM Reservation r
       WHERE r.field = :field
         AND r.date  = :date
         AND r.status <> com.example.demo.tereni.model.Reservation$Status.CANCELLED
         AND (r.startTime < :endTime AND r.endTime > :startTime)
    """)
    boolean existsOverlapping(@Param("field") Field field,
                              @Param("date") LocalDate date,
                              @Param("startTime") LocalTime startTime,
                              @Param("endTime") LocalTime endTime);
    
    
 // Agregat "kao kod druga": naziv terena, datum, broj rezervacija, suma cena
    @Query("""
        SELECT f.name, r.date, COUNT(r), COALESCE(SUM(r.totalPrice), 0)
        FROM Reservation r
        JOIN r.field f
        GROUP BY f.id, f.name, r.date
        ORDER BY f.name, r.date
    """)
    List<Object[]> fetchReservationStats();
}

