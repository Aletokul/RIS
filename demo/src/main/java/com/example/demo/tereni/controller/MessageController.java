package com.example.demo.tereni.controller;

import com.example.demo.tereni.model.User;
import com.example.demo.tereni.service.MessageService;
import com.example.demo.tereni.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/messages")
public class MessageController {

    private final MessageService messageService;
    private final UserRepository userRepository;

    public MessageController(MessageService messageService, UserRepository userRepository) {
        this.messageService = messageService;
        this.userRepository = userRepository;
    }

    @GetMapping("/{friendId}")
    public String viewConversation(@PathVariable Long friendId,
                                   HttpSession session,
                                   Model model) {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/login";
        }

        User friend = userRepository.findById(friendId)
                .orElseThrow(() -> new IllegalArgumentException("Prijatelj nije pronađen."));

        model.addAttribute("friend", friend);
        model.addAttribute("messages", messageService.getConversation(currentUser, friend));

        return "message"; // message.jsp
    }


    @PostMapping("/{friendId}")
    public String sendMessage(@PathVariable Long friendId, @RequestParam String content, HttpSession session) {
        User me = (User) session.getAttribute("user");
        if (me == null) return "redirect:/login";

        User friend = userRepository.findById(friendId).orElseThrow();
        messageService.sendMessage(me, friend, content);
        return "redirect:/messages/" + friendId;
    }
}
