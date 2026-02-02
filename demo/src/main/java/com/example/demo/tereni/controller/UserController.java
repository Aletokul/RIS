package com.example.demo.tereni.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.tereni.model.User;
import com.example.demo.tereni.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {
	
	@Autowired
	UserService userService;
	
	@GetMapping("/register")
	public String showRegisterForm() {
		return "register";
	}
	
	@PostMapping("/register")
	public String register(@RequestParam String username,
							@RequestParam String email,
							@RequestParam String password,
							Model model) {
		try {
			User user = new User();
			user.setUsername(username);
			user.setEmail(email);
			user.setPassword(password);
			userService.registerUser(user);
			return "redirect:/login";
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
			return "register";
		}
	}
	
	@GetMapping("/login")
	public String showLoginForm() {
		return "login";
	}
	
	@PostMapping("/login")
	public String login(@RequestParam String username,
						@RequestParam String password,
						Model model,
						HttpSession session) {
		var userOpt = userService.login(username, password);
		if(userOpt.isPresent()) {
			session.setAttribute("user", userOpt.get());
			return "redirect:/";
		} else {
			model.addAttribute("error", "Pogresan username ili sifra");
			return "login";
		}
	}
	
	@GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

}
