package com.coderscampus.assignment14.service;

import com.coderscampus.assignment14.domain.Channel;
import com.coderscampus.assignment14.repository.ChannelRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChannelService {
    private final ChannelRepository channelRepository;
    private List<Channel> channels;

    public ChannelService(ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
        channels = new ArrayList<>();
    }

    public Channel findById(Long channelId) {
        return channels.get((int) (channelId-1));
    }

    public List<Channel> findAllChannels() {
        if (channels.isEmpty()) {
            addChannel("General");
        }
        return channels;
    }

    public Channel addChannel(String channelName) {
        Channel newChannel = new Channel();
        newChannel.setName(channelName);
        newChannel.setId(channels.size());
        channels.add(newChannel);
        return newChannel;
    }
}
