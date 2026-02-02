package com.example.demo.tereni.repository;

import com.example.demo.tereni.model.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FieldRepository extends JpaRepository<Field, Long> {

    boolean existsByNameAndLocation(String name, String location);

    //Filtriranje po tipu
    List<Field> findByType(String type);
}
