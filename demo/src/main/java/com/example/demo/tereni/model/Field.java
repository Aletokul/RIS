package com.example.demo.tereni.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "fields",
       uniqueConstraints = @UniqueConstraint(name = "uq_field_name_location", columnNames = {"name","location"}))
public class Field {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String name;

    private String location;

    private String type;

    @Column(name="price_per_hour", nullable=false, precision=38, scale=2)
    private BigDecimal pricePerHour;

    @Column(name="is_indoor", nullable=false)
    private boolean indoor;

    private Double rating;

    // ---- GETTERI / SETTERI ----
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public BigDecimal getPricePerHour() { return pricePerHour; }
    public void setPricePerHour(BigDecimal pricePerHour) { this.pricePerHour = pricePerHour; }

    public boolean isIndoor() { return indoor; }
    public void setIndoor(boolean indoor) { this.indoor = indoor; }

    public Double getRating() { return rating; }
    public void setRating(Double rating) { this.rating = rating; }
}
