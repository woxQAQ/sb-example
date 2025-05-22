package com.woxqaq.im.core.handler;

import com.woxqaq.im.common.exception.IMException;
import com.woxqaq.im.common.kit.HeartBeatHandler;
import com.woxqaq.im.common.pojo.UserInfo;
import com.woxqaq.im.common.protocol.Command;
import com.woxqaq.im.common.protocol.Request;
import com.woxqaq.im.core.kit.RouteHandler;
import com.woxqaq.im.core.kit.ServerHeartBeatHandler;
import com.woxqaq.im.core.utils.ChannelManager;
import com.woxqaq.im.core.utils.SpringBeanFactory;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandler;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ChannelHandler.Sharable
public class SocketHandler extends SimpleChannelInboundHandler<Request> {
    private static final AttributeKey<String> ATTR_KEY_READER_TIME = AttributeKey.valueOf("readerTime");

    @Override
    public void channelInactive(ChannelHandlerContext context) throws Exception {
        UserInfo userInfo = ChannelManager.getUserInfo((NioSocketChannel) context.channel());
        if (userInfo != null) {
            log.warn("[{}] channel inactive", userInfo.getUsername());

            RouteHandler routeHandler = SpringBeanFactory.getBean(RouteHandler.class);
            routeHandler.userOffLine(userInfo, (NioSocketChannel) context.channel());

            context.channel().close();
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext context, Object event) throws Exception {
        if (event instanceof IdleStateEvent evt) {
            if (evt.state() == IdleState.READER_IDLE) {
                log.info("Reader IDLE!!");

                HeartBeatHandler heartBeatHandler = SpringBeanFactory.getBean(ServerHeartBeatHandler.class);
                heartBeatHandler.process(context);
            }
        }
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
