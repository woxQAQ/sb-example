package com.woxqaq.im.common.utils;

import com.woxqaq.im.common.exception.IMException;
import com.woxqaq.im.common.pojo.RouteInfo;

import lombok.NoArgsConstructor;

import static com.woxqaq.im.common.enums.StatusEnum.VALIDATION_FAIL;;

@NoArgsConstructor
public class ParseRouteInfo {
    public static RouteInfo parse(String info){
        try {
            String[] split = info.split(":");
            return new RouteInfo(split[0], Integer.parseInt(split[1]), Integer.parseInt(split[2]));
        }
        catch (Exception e){
            throw new IMException(VALIDATION_FAIL, "invalid route info");
        }
    }

    public static String parse(RouteInfo info){
        return info.getIp() + ":" + info.getPort() + ":" + info.getHttpPort();
    }
}
