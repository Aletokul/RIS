package com.example.demo.tereni.controller;

import com.example.demo.tereni.model.Field;
import com.example.demo.tereni.model.User;
import com.example.demo.tereni.service.FieldService;
import com.example.demo.tereni.service.ReservationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalTime;

@Controller
@RequestMapping("/reservations")
public class ReservationController {

    private final FieldService fieldService;
    private final ReservationService reservationService;

    public ReservationController(FieldService fieldService, ReservationService reservationService) {
        this.fieldService = fieldService;
        this.reservationService = reservationService;
    }

    @GetMapping
    public String listReservations(Model model, HttpSession session) {
        // Ako želiš listu mojih rezervacija:
        User u = (User) session.getAttribute("user");
        if (u != null) {
            model.addAttribute("reservations", reservationService.myReservations(u.getUsername()));
        }
        return "reservations";
    }

    @GetMapping("/mine")
    public String myReservations(Model model, HttpSession session) {
        User u = (User) session.getAttribute("user");
        if (u == null) return "redirect:/login";
        model.addAttribute("reservations", reservationService.myReservations(u.getUsername()));
        return "reservations";
    }

    @GetMapping("/new")
    public String newReservation(
            @RequestParam(name = "fieldId", required = false) Long fieldId,
            Model model,
            HttpSession session
    ) {

        if (fieldId == null) {
            model.addAttribute("fields", fieldService.findAll());
            return "reservationSelect";
        }

        Field field = fieldService.get(fieldId);
        model.addAttribute("field", field);
        model.addAttribute("today", LocalDate.now());
        model.addAttribute("defaultStart", LocalTime.of(18, 0));
        model.addAttribute("defaultEnd", LocalTime.of(19, 0));
        return "reservationNew";
    }

    @PostMapping
    public String createReservation(
            @RequestParam Long fieldId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam("startTime") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime,
            @RequestParam("endTime")   @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime endTime,
            @RequestParam(name = "notes", required = false) String notes,
            HttpSession session
    ) {
        User u = (User) session.getAttribute("user");
        if (u == null) return "redirect:/login";

        // >>> OVDE SE KREIRA REZERVACIJA
        reservationService.create(u.getUsername(), fieldId, date, startTime, endTime, notes);

        // Prebaci korisnika na svoje rezervacije
        return "redirect:/reservations/mine";
    }
    
    
    
    @GetMapping("/admin/pending")
    public String adminPending(Model model, HttpSession session) {
        User u = (User) session.getAttribute("user");
        if (u == null) return "redirect:/login";
        if (u.getRole() == User.Role.USER) return "redirect:/?error=forbidden";

        model.addAttribute("pending", reservationService.pendingReservations());
        return "adminReservations";
    }

    /** ADMIN odobrava */
    @PostMapping("/{id}/approve")
    public String approve(@PathVariable Long id, HttpSession session, RedirectAttributes ra) {
        User u = (User) session.getAttribute("user");
        if (u == null) return "redirect:/login";
        if (u.getRole() == User.Role.USER) return "redirect:/?error=forbidden";

        reservationService.adminApprove(id);
        ra.addFlashAttribute("msg", "Rezervacija #" + id + " odobrena.");
        return "redirect:/reservations/admin/pending";
    }

    /** ADMIN odbija (postavlja CANCELLED) */
    @PostMapping("/{id}/reject")
    public String reject(@PathVariable Long id, HttpSession session, RedirectAttributes ra) {
        User u = (User) session.getAttribute("user");
        if (u == null) return "redirect:/login";
        if (u.getRole() == User.Role.USER) return "redirect:/?error=forbidden";

        reservationService.adminReject(id);
        ra.addFlashAttribute("msg", "Rezervacija #" + id + " odbijena.");
        return "redirect:/reservations/admin/pending";
    }
    
    @PostMapping("/{id}/cancel")
    public String cancel(@PathVariable Long id, HttpSession session) {
        User u = (User) session.getAttribute("user");
        if (u == null) return "redirect:/login";
        reservationService.cancel(id);
        return "redirect:/reservations/mine";
    }

}
