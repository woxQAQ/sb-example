package com.woxqaq.im.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.woxqaq.im.common.protocol.Command;
import com.woxqaq.im.common.protocol.Request;

@Configuration
public class BeanConfig {
    @Bean(value = "heartBeat")
    public Request heartBeat() {
        return Request.newBuilder()
                .setRequestId(0L)
                .setReqMsg("pong")
                .setCmd(Command.PING)
                .build();
    }
}
