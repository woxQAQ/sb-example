package com.woxqaq.im.common.kit;

import io.netty.channel.ChannelHandlerContext;

public interface HeartBeatHandler {
    void process(ChannelHandlerContext ctx) throws Exception;
}
