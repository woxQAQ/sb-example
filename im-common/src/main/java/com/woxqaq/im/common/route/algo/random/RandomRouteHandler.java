package com.woxqaq.im.common.route.algo.random;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static com.woxqaq.im.common.enums.StatusEnum.SERVER_NOT_AVAILABLE;

import com.woxqaq.im.common.exception.IMException;
import com.woxqaq.im.common.pojo.RouteInfo;
import com.woxqaq.im.common.route.RouteHandler;
import com.woxqaq.im.common.utils.ParseRouteInfo;

public class RandomRouteHandler implements RouteHandler {
    private List<String> cluster;

    @Override
    public String routeServer(List<String> cluster, String key) {
        int size = cluster.size();
        if (size == 0) {
            throw new IMException(SERVER_NOT_AVAILABLE);
        }
        this.cluster = cluster;
        int index = ThreadLocalRandom.current().nextInt(size);

        return cluster.get(index);
    }

    @Override
    public List<String> removeExpireServer(RouteInfo routeInfo) {
        String res = ParseRouteInfo.parse(routeInfo);
        cluster.removeIf(v -> v.equals(res));
        return cluster;
    }

}
