package com.coderscampus.assignment14.repository;

import com.coderscampus.assignment14.domain.Channel;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class ChannelRepository {
    private final List<Channel> channels = new ArrayList<>();
    private final AtomicInteger idGenerator = new AtomicInteger(0);

    public List<Channel> findAll() {
        return channels;
    }

    public Channel findById(Integer id) {
        return channels.stream()
                .filter(channel -> channel.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Channel save(Channel channel) {
        if (channel.getId() == null) {
            channel.setId(idGenerator.getAndIncrement());
        }
        channels.add(channel);
        return channel;
    }
}
