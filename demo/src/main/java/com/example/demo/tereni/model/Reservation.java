package com.example.demo.tereni.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name="reservations",
       indexes = {
           @Index(name="idx_res_date", columnList="date"),
           @Index(name="idx_res_field", columnList="field_id"),
           @Index(name="idx_res_user",  columnList="user_id"),
           @Index(name="idx_res_field_date_start", columnList="field_id,date,start_time")
       })
public class Reservation {

    public enum Status { PENDING, CONFIRMED, CANCELLED }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional=false)
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @ManyToOne(optional=false)
    @JoinColumn(name="field_id", nullable=false)
    private Field field;

    @Column(nullable=false)
    private LocalDate date;

    @Column(name="start_time", nullable=false)
    private LocalTime startTime;

    @Column(name="end_time", nullable=false)
    private LocalTime endTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private Status status = Status.PENDING;

    @Column(name="total_price", nullable=false, precision=38, scale=2)
    private BigDecimal totalPrice;

    @Column(name="created_at", nullable=false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(length=500)
    private String notes;

    // ---- GETTERI / SETTERI ----
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Field getField() { return field; }
    public void setField(Field field) { this.field = field; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public LocalTime getStartTime() { return startTime; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }

    public LocalTime getEndTime() { return endTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public BigDecimal getTotalPrice() { return totalPrice; }
    public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
