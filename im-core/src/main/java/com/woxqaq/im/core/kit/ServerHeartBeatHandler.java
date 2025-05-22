package com.woxqaq.im.core.kit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.woxqaq.im.common.kit.HeartBeatHandler;
import com.woxqaq.im.common.route.RouteHandler;
import com.woxqaq.im.core.config.AppConfig;

import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ServerHeartBeatHandler implements HeartBeatHandler {

    @Autowired
    private RouteHandler routeHandler;

    @Autowired
    private AppConfig appConfig;

    @Override
    public void process(ChannelHandlerContext ctx) throws Exception {

    }

}
