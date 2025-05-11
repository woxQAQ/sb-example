package com.woxqaq.im.core.handler;

import com.woxqaq.im.common.pojo.UserInfo;
import com.woxqaq.im.common.protocol.Command;
import com.woxqaq.im.common.protocol.Request;
import com.woxqaq.im.core.utils.ChannelManager;
import com.woxqaq.im.core.utils.SpringBeanFactory;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SocketHandler extends SimpleChannelInboundHandler<Request> {
    private static final AttributeKey<String> ATTR_KEY_READER_TIME = AttributeKey.valueOf("readerTime");

    @Override
    public void channelInactive(ChannelHandlerContext context) throws Exception {
        UserInfo userInfo = ChannelManager.getUserInfo((NioSocketChannel) context.channel());
        if (userInfo != null) {
            log.warn("[{}] channel inactive", userInfo.getUsername());

            // todo: clear route info
            // fixme(woxQAQ)

            context.channel().close();
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext context, Object event) throws Exception {
        super.userEventTriggered(context, event);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Request msg) throws Exception {
        log.info("receive msg = {}", msg.toString());
        if (msg.getCmd() == Command.LOGIN_REQUEST) {
            ChannelManager.put(msg.getRequestId(), (NioSocketChannel) ctx.channel());
            ChannelManager.putSession(msg.getRequestId(), msg.getReqMsg());
            log.info("user [{}] online success", msg.getReqMsg());
        }
        if (msg.getCmd() == Command.PING) {
            ctx.channel().attr(ATTR_KEY_READER_TIME).set(System.currentTimeMillis() + "");
            Request req = SpringBeanFactory.getBean("heartbeat", Request.class);
            ctx.writeAndFlush(req).addListener((ChannelFutureListener) future -> {
                if (!future.isSuccess()) {
                    log.error("IO error, close channel");
                    future.channel().close();
                }
            });
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error(cause.getMessage(), cause);
    }

}
