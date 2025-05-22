package com.woxqaq.im.common.config;

import org.springframework.beans.factory.annotation.Value;

import lombok.Data;

@Data
public class GeneralServerApplication {
    @Value("${meta.uri}")
    private String metaUri;

    @Value("${meta.switch}")
    private Boolean metaswitch;

    @Value("${meta.timeout}")
    private Integer metaTimeout;

    @Value("${server.port}")
    private int port;
}
