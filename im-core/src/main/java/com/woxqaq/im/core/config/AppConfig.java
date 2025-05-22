package com.woxqaq.im.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.woxqaq.im.common.config.GeneralServerApplication;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Component
@Data
@EqualsAndHashCode(callSuper = true)
public class AppConfig extends GeneralServerApplication {
    @Value("${im.route.url}")
    private int routeUrl;

    @Value("${im.heartbeat.time}")
    private long heartBeatTime;

    @Value("${im.port}")
    private int imPort;
}
