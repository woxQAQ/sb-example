package com.woxqaq.im.common.utils;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;


public class NettyAttrUtil {
    private static final AttributeKey<String> ATTR_KEY_READER_TIME = AttributeKey.valueOf("readerTime");

    public static Long getReaderTime(Channel channel) {
        String val = channel.attr(ATTR_KEY_READER_TIME).get();
        if (val != null) {
            return Long.valueOf(val);
        }
        return null;
    }
}
