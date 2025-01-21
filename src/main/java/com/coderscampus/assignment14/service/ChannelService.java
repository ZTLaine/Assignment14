package com.coderscampus.assignment14.service;

import com.coderscampus.assignment14.domain.Channel;
import com.coderscampus.assignment14.repository.ChannelRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChannelService {
    private final ChannelRepository channelRepository;

    public ChannelService(ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }

    public Channel findById(Long channelId) {
        return channelRepository.findById(channelId.intValue());
    }

    public List<Channel> findAllChannels() {
        List<Channel> channels = channelRepository.findAll();
        if (channels.isEmpty()) {
            addChannel("General");
        }
        return channelRepository.findAll();
    }

    public Channel addChannel(String channelName) {
        if (channelRepository.findAll().stream()
                .anyMatch(c -> c.getName().equalsIgnoreCase(channelName))) {
            throw new IllegalArgumentException("Channel name already exists");
        }

        Channel newChannel = new Channel();
        newChannel.setName(channelName);
        newChannel.setId(channelRepository.findAll().size());
        return channelRepository.save(newChannel);
    }
}
