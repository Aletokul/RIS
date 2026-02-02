package com.example.demo.tereni.controller;

import com.example.demo.tereni.model.Field;
import com.example.demo.tereni.model.User;
import com.example.demo.tereni.service.FieldService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/fields")
public class FieldController {

	@Autowired
    FieldService fields;


	@GetMapping
	public String list(@RequestParam(required = false) String type, Model model) {

	    if (type != null && !type.isBlank()) {
	        model.addAttribute("fields", fields.findByType(type.trim()));
	        //model.addAttribute("selectedType", type.trim());
	    } else {
	        model.addAttribute("fields", fields.findAll());
	    }

	    return "fields";
	}


    @GetMapping("/new")
    public String newField(HttpSession session, Model model) {
        User u = (User) session.getAttribute("user");

        if (u == null) {
            return "redirect:/login";
        }

        if (u.getRole() == User.Role.USER) {
            return "redirect:/?error=forbidden";
        }

        model.addAttribute("field", new Field());
        return "fieldForm";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("field") Field field,
                         BindingResult br,
                         Model model) {
        if (br.hasErrors()) return "fieldForm";
        try {
            fields.create(field);
            return "redirect:/fields";
        } catch (IllegalArgumentException ex) {
            model.addAttribute("error", ex.getMessage());
            return "fieldForm";
        }
    }
    
    
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, HttpSession session, Model model) {
        User u = (User) session.getAttribute("user");
        if (u == null) return "redirect:/login";
        if (u.getRole() == User.Role.USER) return "redirect:/?error=forbidden";

        Field existing = fields.get(id);
        model.addAttribute("field", existing);

        
        return "fieldEdit";
    }

    // UPDATE (snimanje izmene)
    @PostMapping("/{id}/edit")
    public String editSubmit(@PathVariable Long id,
                             @ModelAttribute Field form,
                             HttpSession session,
                             Model model) {
        User u = (User) session.getAttribute("user");
        if (u == null) return "redirect:/login";
        if (u.getRole() == User.Role.USER) return "redirect:/?error=forbidden";

        try {
            fields.update(id, form);
            return "redirect:/fields";
        } catch (IllegalArgumentException ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("field", fields.get(id));
            return "fieldEdit"; // tvoj posebni JSP za edit
        }
    }


    // DELETE
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, HttpSession session) {
        User u = (User) session.getAttribute("user");
        if (u == null) return "redirect:/login";
        if (u.getRole() == User.Role.USER) return "redirect:/?error=forbidden";

        // Ako FK blokira brisanje (npr. rezervacije), postavi ON DELETE CASCADE ili validiraj pre brisanja
        fields.delete(id); // dodaj u servis
        return "redirect:/fields";
    }
}
