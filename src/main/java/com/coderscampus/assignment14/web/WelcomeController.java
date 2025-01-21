package com.coderscampus.assignment14.web;

import com.coderscampus.assignment14.domain.Channel;
import com.coderscampus.assignment14.domain.User;
import com.coderscampus.assignment14.service.ChannelService;
import com.coderscampus.assignment14.service.UserService;
import com.coderscampus.assignment14.web.response.ApiResponse;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class WelcomeController {
    private final UserService userService;
    private final ChannelService channelService;

    WelcomeController(UserService userService, ChannelService channelService) {
        this.userService = userService;
        this.channelService = channelService;
    }

    @GetMapping("/welcome")
    public String getWelcome(Model model, HttpSession session) {
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
}
