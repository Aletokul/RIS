package com.example.demo.tereni.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.tereni.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
    boolean existsByUsername(String username);

    // za pretragu po korisničkom imenu (u /friends/search)
    List<User> findByUsernameContainingIgnoreCase(String username);
}
