package com.coderscampus.assignment14.web;

import com.coderscampus.assignment14.domain.User;
import com.coderscampus.assignment14.service.UserService;
import com.coderscampus.assignment14.web.response.ApiResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class WelcomeController {
    private final UserService userService;

    WelcomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/welcome")
    public String getWelcome(Model model) {
        model.addAttribute("user", new User());
        return "welcome";
    }

    @PostMapping("/welcome")
    @ResponseBody
    public ResponseEntity<?> postWelcome(@RequestBody User user) {
        try {
            userService.addUser(user.getUsername());
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>("ERROR", e.getMessage(), null));
        }
    }

    @GetMapping("/")
    public String root() {
        return "redirect:/welcome";
    }
}
