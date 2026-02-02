package com.example.demo.tereni.model;

import java.math.BigDecimal;
import java.util.Date;

public class ReservationReport {

    private String fieldName;
    private Date day;
    private Long reservationsCount;
    private BigDecimal totalRevenue;

    public ReservationReport() {}

    public ReservationReport(String fieldName, Date day, Long reservationsCount, BigDecimal totalRevenue) {
        this.fieldName = fieldName;
        this.day = day;
        this.reservationsCount = reservationsCount;
        this.totalRevenue = totalRevenue;
    }

    public String getFieldName() { return fieldName; }
    public void setFieldName(String fieldName) { this.fieldName = fieldName; }

    public Date getDay() { return day; }
    public void setDay(Date day) { this.day = day; }

    public Long getReservationsCount() { return reservationsCount; }
    public void setReservationsCount(Long reservationsCount) { this.reservationsCount = reservationsCount; }

    public BigDecimal getTotalRevenue() { return totalRevenue; }
    public void setTotalRevenue(BigDecimal totalRevenue) { this.totalRevenue = totalRevenue; }
}
