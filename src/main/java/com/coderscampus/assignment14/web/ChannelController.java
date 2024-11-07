package com.coderscampus.assignment14.web;

import com.coderscampus.assignment14.service.ChannelService;
import com.coderscampus.assignment14.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
        model.addAttribute("channels", channelService.findAllChannels());
        return "channels";
    }

    @GetMapping("/channel/{channelId}")
    public String getChannel(Model model, @PathVariable Long channelId) {
        model.addAttribute("channel", channelService.findbyId(channelId));
        return "channel";
    }
}
