package com.example.demo.tereni.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "field_reviews",
    uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "field_id"})
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class FieldReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false) @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(optional = false) @JoinColumn(name = "field_id")
    private Field field;

    @Column(nullable = false)
    private Integer rating; // 1–5

    @Lob
    private String comment;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
}
