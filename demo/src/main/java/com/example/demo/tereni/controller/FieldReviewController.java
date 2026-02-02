package com.example.demo.tereni.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.example.demo.tereni.model.Field;
import com.example.demo.tereni.model.User;
import com.example.demo.tereni.service.FieldService;
import com.example.demo.tereni.service.FieldReviewService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/reviews")
public class FieldReviewController {

    @Autowired
    private FieldReviewService reviewService;

    @Autowired
    private FieldService fieldService;

    @PostMapping("/add")
    public String addReview(@RequestParam Long fieldId,
                            @RequestParam Integer rating,
                            @RequestParam(required = false) String comment,
                            HttpSession session) {

        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        Field field = fieldService.get(fieldId); // baca izuzetak ako ne postoji
        reviewService.addReview(user, field, rating, comment);

        return "redirect:/fields"; // ili redirect na detalj terena ako imaš npr. /fields/{id}
    }
}
