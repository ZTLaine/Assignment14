package com.coderscampus.assignment14.repository;

import com.coderscampus.assignment14.domain.Channel;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ChannelRepository {
//    It feels really superfluous to have repo objects at all given I'm not going to interact with a db
//    Maybe scrap this package entirely?
    private final List<Channel> channels;

    public ChannelRepository() {
        this.channels = new ArrayList<>();
    }

    public List<Channel> findAll() {
        return channels;
    }

    public Channel save(Channel channel) {
        channels.add(channel);
        return channel;
    }

    public Channel findById(Integer id) {
        return channels.stream()
                .filter(channel -> channel.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
