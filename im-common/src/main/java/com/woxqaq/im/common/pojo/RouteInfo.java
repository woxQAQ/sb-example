package com.woxqaq.im.common.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RouteInfo {
    private String ip;
    private int port;
    private int httpPort;
}
