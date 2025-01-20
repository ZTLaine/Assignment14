package com.coderscampus.assignment14.web;

import com.coderscampus.assignment14.domain.Channel;
import com.coderscampus.assignment14.service.ChannelService;
import com.coderscampus.assignment14.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ChannelController {
    private final ChannelService channelService;

    public ChannelController(ChannelService channelService) {
        this.channelService = channelService;
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
            return "redirect:/channels";
        }
        model.addAttribute("channel", channel);
        return "channel";
    }

    @PostMapping("/channels/addChannel")
    public String addChannel(@RequestParam String name) {
        Channel newChannel = channelService.addChannel(name);
        return "redirect:/channel/" + newChannel.getId();
    }
}
