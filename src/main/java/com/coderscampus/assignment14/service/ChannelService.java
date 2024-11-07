package com.coderscampus.assignment14.service;

import com.coderscampus.assignment14.domain.Channel;
import com.coderscampus.assignment14.repository.ChannelRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChannelService {
    private ChannelRepository channelRepository;

    private List<Channel> channels;

    public Channel findbyId(Long channelId) {
        return channels.get((int) (channelId-1));
    }

    public List<Channel> findAllChannels() {
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
