package com.coderscampus.assignment14.repository;

import com.coderscampus.assignment14.domain.Channel;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ChannelRepository {
//    It feels really superfluous to have repo objects at all given I'm not going to interact with a db
//    Maybe scrap this package entirely?
    private List<Channel> channels = new ArrayList<Channel>();
}
