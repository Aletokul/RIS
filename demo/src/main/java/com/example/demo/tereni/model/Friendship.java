package com.example.demo.tereni.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
    name = "friendships",
    uniqueConstraints = @UniqueConstraint(columnNames = {"sender_id", "receiver_id"})
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Friendship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional=false)
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne(optional=false)
    @JoinColumn(name = "receiver_id")
    private User receiver;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    @Builder.Default
    private Status status = Status.PENDING;

    public enum Status { PENDING, ACCEPTED, REJECTED }
}
