package com.woxqaq.im.core.server.impl;

import java.net.InetSocketAddress;

import org.springframework.stereotype.Service;

import com.woxqaq.im.common.protocol.Command;
import com.woxqaq.im.common.protocol.Request;
import com.woxqaq.im.core.handler.SockerInitiler;
import com.woxqaq.im.core.models.SendMessageRequest;
import com.woxqaq.im.core.server.ImServer;
import com.woxqaq.im.core.utils.ChannelManager;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ImServerImpl implements ImServer {
    private EventLoopGroup boss = new NioEventLoopGroup();
    private EventLoopGroup work = new NioEventLoopGroup();

    private int nettyPort;

    public void start() throws InterruptedException {
        ServerBootstrap bootstrap = new ServerBootstrap()
                .group(boss, work)
                .channel(NioServerSocketChannel.class)
                .localAddress(new InetSocketAddress(nettyPort))
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new SockerInitiler());
        ChannelFuture future = bootstrap.bind().sync();
        if (future.isSuccess()) {
            log.info("netty server start success, port: {}", nettyPort);
        }
    }

    @PreDestroy
    public void destory() {
        boss.shutdownGracefully();
        work.shutdownGracefully();
        log.info("netty server destory");
    }

    public void sendMessage(SendMessageRequest req) {
        NioSocketChannel ch = ChannelManager.get(req.getUserId());

        if (null == ch) {
            log.error("no such client {}", req.getUserId());
            return;
        }

        Request _req = Request.newBuilder()
                .setRequestId(req.getUserId())
                .setReqMsg(req.getMsg())
                .putAllProperties(req.getOptions())
                .setCmd(Command.MESSAGE)
                .build();

        ch.writeAndFlush(_req).addListener(
                f -> {
                    log.debug("server: push msg:{}", req.toString());
                });
    }
}
