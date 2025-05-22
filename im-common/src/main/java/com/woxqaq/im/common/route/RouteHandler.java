package com.woxqaq.im.common.route;

import java.util.List;

import com.woxqaq.im.common.pojo.RouteInfo;

public interface RouteHandler {
    /*
     * route server in a cluster
     */
    String routeServer(List<String> cluster, String key);

    List<String> removeExpireServer(RouteInfo routeInfo);
}
