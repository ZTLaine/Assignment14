package com.coderscampus.assignment14.web;

import com.coderscampus.assignment14.domain.Channel;
import com.coderscampus.assignment14.domain.User;
import com.coderscampus.assignment14.service.ChannelService;
import com.coderscampus.assignment14.service.UserService;
import jakarta.servlet.http.HttpSession;
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
    public String postWelcome(@ModelAttribute User user) {
        User newUser = userService.addUser(user.getUsername());
        System.out.println(newUser.getUsername());


        return "redirect:/channels";
    }
}
