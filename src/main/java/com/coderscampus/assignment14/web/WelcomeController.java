package com.coderscampus.assignment14.web;

import com.coderscampus.assignment14.domain.User;
import com.coderscampus.assignment14.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WelcomeController {
    UserService userService;

    WelcomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/welcome")
    public String getWelcome(Model model, HttpSession session) {
        model.addAttribute("user", new User());
        return "welcome";
    }

    @PostMapping("/welcome")
    public String postWelcome(@RequestParam String username) {
        User newUser = userService.addUser(username);


        return "redirect:/welcome";
    }
}
