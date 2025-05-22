package com.woxqaq.im.router.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.woxqaq.im.common.config.GeneralServerApplication;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Component
@Data
@EqualsAndHashCode(callSuper = true)
public class AppConfig extends GeneralServerApplication {
    @Value("${route.way.route}")
    private String routeWay;

    @Value("${route.way.consistentHash}")
    private String consistentHashWay;
}
