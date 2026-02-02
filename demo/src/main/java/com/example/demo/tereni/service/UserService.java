package com.example.demo.tereni.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.tereni.model.User;
import com.example.demo.tereni.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /** Registracija korisnika */
    @Transactional
    public User registerUser(User raw) {
        if (userRepository.existsByUsername(raw.getUsername())) {
            throw new IllegalArgumentException("Username već postoji");
        }
        if (userRepository.existsByEmail(raw.getEmail())) {
            throw new IllegalArgumentException("Email već postoji");
        }

        User u = new User();
        u.setUsername(raw.getUsername().trim());
        u.setEmail(raw.getEmail().trim());
        
        u.setPassword(raw.getPassword());

        
        return userRepository.save(u);
    }

    /** Login provera username i provera lozinke */
    @Transactional(readOnly = true)
    public Optional<User> login(String username, String password) {
        return userRepository.findByUsername(username)
                .filter(u -> u.getPassword() != null && u.getPassword().equals(password));
    }
}
