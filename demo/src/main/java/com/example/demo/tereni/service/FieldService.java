package com.example.demo.tereni.service;

import com.example.demo.tereni.model.Field;
import com.example.demo.tereni.repository.FieldRepository;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class FieldService {

    private final FieldRepository repo;

    public FieldService(FieldRepository repo) {
        this.repo = repo;
    }

    /** Koristi ga FieldWebController.list() */
    public List<Field> findAll() {
        return repo.findAll();
    }

    /** NEW: tačno filtriranje po type (npr. "fudbal", "kosarka_5v5", "basket_3x3") */
    public List<Field> findByType(String type) {
        return repo.findByType(type);
    }

    @Transactional
    public Field create(Field f) {
        f.setName(f.getName().trim());
        if (f.getLocation() != null) f.setLocation(f.getLocation().trim());
        if (f.getType() != null) f.setType(f.getType().trim());

        if (f.getPricePerHour() == null) {
            f.setPricePerHour(BigDecimal.ZERO);
        }

        if (repo.existsByNameAndLocation(f.getName(), f.getLocation())) {
            throw new IllegalArgumentException("Teren vec postoji");
        }

        return repo.save(f);
    }

    public Field get(Long id) {
        return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Teren ne postoji"));
    }

    @Transactional
    public void delete(Long id) {
        try {
            repo.deleteById(id);
        } catch (DataIntegrityViolationException ex) {
            // desi se ako postoje rezervacije sa FK na ovaj field i nema ON DELETE CASCADE
            throw new IllegalStateException(
                "Teren ne može da se obriše jer postoje povezane rezervacije.",
                ex
            );
        }
    }


    @Transactional
    public Field update(Long id, Field data) {
        Field existing = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Field not found"));

        if (data.getName() != null) existing.setName(data.getName().trim());
        if (data.getLocation() != null) existing.setLocation(data.getLocation().trim());
        if (data.getType() != null) existing.setType(data.getType().trim());
        if (data.getPricePerHour() != null) existing.setPricePerHour(data.getPricePerHour());
        existing.setIndoor(data.isIndoor());

        return repo.save(existing);
    }
}
