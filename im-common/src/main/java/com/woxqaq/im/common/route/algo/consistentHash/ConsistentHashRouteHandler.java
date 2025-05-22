package com.woxqaq.im.common.route.algo.consistentHash;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.woxqaq.im.common.pojo.RouteInfo;
import com.woxqaq.im.common.route.RouteHandler;
import com.woxqaq.im.common.utils.ParseRouteInfo;

import lombok.Setter;

@Setter
public class ConsistentHashRouteHandler implements RouteHandler {
    private AbstractConsistentHash hash;

    @Override
    public String routeServer(List<String> cluster, String key) {
        return hash.process(cluster, key);
    }

    @Override
    public List<String> removeExpireServer(RouteInfo routeInfo) {
        Map<String, String> res = hash.remove(ParseRouteInfo.parse(routeInfo));
        return new ArrayList<>(res.keySet());
    }

}
