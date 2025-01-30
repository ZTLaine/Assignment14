package com.coderscampus.assignment14.web;

import com.coderscampus.assignment14.domain.Channel;
import com.coderscampus.assignment14.service.ChannelService;
import com.coderscampus.assignment14.service.UserService;
import com.coderscampus.assignment14.web.response.ApiResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import lombok.Data;

@Controller
public class ChannelController {
    private final ChannelService channelService;
    private final UserService userService;

    public ChannelController(ChannelService channelService, UserService userService) {
        this.channelService = channelService;
        this.userService = userService;
    }

    @GetMapping("/channels")
    public String channels(Model model) {
        List<Channel> channels = channelService.findAllChannels();
        model.addAttribute("channels", channels);
        return "channels";
    }

    @GetMapping("/channel/{channelId}")
    public String getChannel(Model model, @PathVariable Long channelId) {
        Channel channel = channelService.findById(channelId);
        if (channel == null) {
            model.addAttribute("message", "Channel not found.");
            return "error";
        }
        model.addAttribute("channel", channel);
        return "channel";
    }

    @PostMapping("/channels/addChannel")
    @ResponseBody
    public ResponseEntity<?> addChannel(@RequestBody CreateChannelRequest request) {
        try {
            if (request.getUsername() == null || userService.findByUsername(request.getUsername()) == null) {
                return ResponseEntity.status(401)
                    .body(new ApiResponse<>("ERROR", "You must be logged in to create a channel", null));
            }
            
            Channel newChannel = channelService.addChannel(request.getName());
            return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "Channel created successfully", newChannel));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>("ERROR", e.getMessage(), null));
        }
    }
}

@Data
class CreateChannelRequest {
    private String name;
    private String username;
}
