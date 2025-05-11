package com.woxqaq.im.core.handler;

import com.woxqaq.im.common.protocol.Request;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;

public class SockerInitiler extends ChannelInitializer<Channel> {

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ch.pipeline()
                .addLast(new IdleStateHandler(11, 0, 0))
                .addLast(new ProtobufVarint32FrameDecoder())
                .addLast(new ProtobufEncoder())
                .addLast(new SocketHandler())
                .addLast(new ProtobufVarint32FrameDecoder())
                .addLast(new ProtobufDecoder(Request.getDefaultInstance()));
    }

}
