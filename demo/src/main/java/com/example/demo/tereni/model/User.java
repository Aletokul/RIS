package com.example.demo.tereni.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true)
    private String username;

    @Column(nullable=false)
    private String password;

    @Column(nullable=false, unique=true)
    private String email;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private Role role = Role.USER;

    public enum Role {
        USER, ADMIN
    }
}
