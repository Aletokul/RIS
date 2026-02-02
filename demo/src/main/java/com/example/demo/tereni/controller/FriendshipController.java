package com.example.demo.tereni.controller;

import com.example.demo.tereni.model.User;
import com.example.demo.tereni.service.FriendshipService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/friends")
public class FriendshipController {

    private final FriendshipService friendshipService;

    public FriendshipController(FriendshipService friendshipService) {
        this.friendshipService = friendshipService;
    }

    // Lista: prijatelji + pending
    @GetMapping
    public String index(HttpSession session, Model model) {
        User u = (User) session.getAttribute("user");
        if (u == null) return "redirect:/login";

        model.addAttribute("friends", friendshipService.myFriends(u.getId()));
        model.addAttribute("pendingForMe", friendshipService.pendingForMe(u.getId()));
        model.addAttribute("myPending", friendshipService.myPending(u.getId()));
        return "friends"; // friends.jsp
    }

    // Slanje zahteva po username-u (poklapa se sa tvojom formom action="/friends/request")
    @PostMapping("/request")
    public String requestByUsername(
            @RequestParam String username,
            HttpSession session,
            RedirectAttributes ra
    ) {
        User u = (User) session.getAttribute("user");
        if (u == null) return "redirect:/login";

        try {
            friendshipService.sendRequestByUsername(u.getId(), username);
            ra.addFlashAttribute("msg", "Zahtev poslat korisniku @" + username + ".");
        } catch (IllegalStateException | IllegalArgumentException ex) {
            ra.addFlashAttribute("err", ex.getMessage());
        }
        return "redirect:/friends";
    }

    // Prihvatanje
    @PostMapping("/{id}/accept")
    public String accept(@PathVariable("id") Long friendshipId, HttpSession session, RedirectAttributes ra) {
        User u = (User) session.getAttribute("user");
        if (u == null) return "redirect:/login";
        try {
            friendshipService.accept(friendshipId, u.getId());
            ra.addFlashAttribute("msg", "Zahtev prihvaćen.");
        } catch (RuntimeException ex) {
            ra.addFlashAttribute("err", ex.getMessage());
        }
        return "redirect:/friends";
    }

    // Odbijanje
    @PostMapping("/{id}/decline")
    public String decline(@PathVariable("id") Long friendshipId, HttpSession session, RedirectAttributes ra) {
        User u = (User) session.getAttribute("user");
        if (u == null) return "redirect:/login";
        try {
            friendshipService.decline(friendshipId, u.getId());
            ra.addFlashAttribute("msg", "Zahtev odbijen.");
        } catch (RuntimeException ex) {
            ra.addFlashAttribute("err", ex.getMessage());
        }
        return "redirect:/friends";
    }
}
