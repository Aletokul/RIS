package com.example.demo.tereni.controller;

import com.example.demo.tereni.model.Field;
import com.example.demo.tereni.service.FieldService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fields")
@CrossOrigin(origins = "http://localhost:4200")
public class FieldRestController {

    private final FieldService fieldService;

    public FieldRestController(FieldService fieldService) {
        this.fieldService = fieldService;
    }

    @GetMapping
    public List<Field> getAll() {
        return fieldService.findAll();
    }

    @GetMapping("/{id}")
    public Field getOne(@PathVariable Long id) {
        return fieldService.get(id);
    }
}
