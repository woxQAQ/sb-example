package com.woxqaq.im.common.route.algo.loop;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import com.woxqaq.im.common.exception.IMException;
import com.woxqaq.im.common.pojo.RouteInfo;
import com.woxqaq.im.common.route.RouteHandler;
import com.woxqaq.im.common.utils.ParseRouteInfo;

import static com.woxqaq.im.common.enums.StatusEnum.SERVER_NOT_AVAILABLE;

public class LoopHandler implements RouteHandler {
    private final AtomicLong index = new AtomicLong(0L);

    private List<String> cluster;

    @Override
    public String routeServer(List<String> cluster, String key) {
        if (cluster.size() == 0) {
            throw new IMException(SERVER_NOT_AVAILABLE);
        }
        this.cluster = cluster;
        Long pos = index.getAndIncrement() % cluster.size();
        if (pos < 0) {
            pos = 0l;
        }
        return cluster.get(pos.intValue());

    }

    @Override
    public List<String> removeExpireServer(RouteInfo routeInfo) {
        String res = ParseRouteInfo.parse(routeInfo);
        cluster.removeIf(v -> v.equals(res));
        return cluster;
    }

}
