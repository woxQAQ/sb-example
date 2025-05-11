package com.woxqaq.im.core.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.woxqaq.im.common.pojo.UserInfo;

import io.netty.channel.socket.nio.NioSocketChannel;

public class ChannelManager {
    private static final Map<Long, NioSocketChannel> CHAN_MAP = new ConcurrentHashMap<>(16);
    private static final Map<Long, String> SESSION_MAP = new ConcurrentHashMap<>(16);

    public static NioSocketChannel get(Long userId) {
        return CHAN_MAP.get(userId);
    }

    public static void put(Long reqId, NioSocketChannel nioSocketChannel) {
        CHAN_MAP.put(reqId, nioSocketChannel);
    }

    public static void putSession(Long reqId, String username) {
        SESSION_MAP.put(reqId, username);
    }

    public static UserInfo getUserInfo(NioSocketChannel nioSocketChannel) {
        for (Map.Entry<Long, NioSocketChannel> e : CHAN_MAP.entrySet()) {
            NioSocketChannel chan = e.getValue();
            if (chan == nioSocketChannel) {
                Long key = e.getKey();
                String username = SESSION_MAP.get(key);
                return new UserInfo(key, username);
            }
        }
        return null;
    }
}
