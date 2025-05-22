package com.woxqaq.im.core.kit;

import com.woxqaq.im.common.pojo.UserInfo;
import com.woxqaq.im.common.utils.NettyAttrUtil;
import com.woxqaq.im.core.utils.ChannelManager;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.woxqaq.im.common.kit.HeartBeatHandler;
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
        long heartBeatTime = appConfig.getHeartBeatTime();

        Long lastReadTime = NettyAttrUtil.getReaderTime(ctx.channel());
        long now = System.currentTimeMillis();

        if (lastReadTime != null && now - lastReadTime > heartBeatTime) {
            UserInfo userInfo = ChannelManager.getUserInfo((NioSocketChannel) ctx.channel());
            if (userInfo != null) {
                log.warn("client {} heartbeat timeout {} ms, need to close", userInfo.getUsername(),now-lastReadTime);
            }
            routeHandler.userOffLine(userInfo,(NioSocketChannel) ctx.channel());
            ctx.channel().close();
        }
    }

}
