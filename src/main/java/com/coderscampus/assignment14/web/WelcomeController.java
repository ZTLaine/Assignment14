package com.coderscampus.assignment14.web;

import com.coderscampus.assignment14.domain.User;
import com.coderscampus.assignment14.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class WelcomeController {
    UserService userService;

    WelcomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/welcome")
    public String getWelcome(Model model) {
        model.addAttribute(new User());
        return "welcome";
    }

    @PostMapping("/welcome")
    public String postWelcome(@ModelAttribute("user") User newUser) {
        userService.addUser(newUser);

        return "redirect:/channels";
    }
}
