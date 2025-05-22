package com.woxqaq.im.core.kit;

import com.woxqaq.im.api.RouterAPI;
import com.woxqaq.im.api.models.ChatReq;
import com.woxqaq.im.common.pojo.UserInfo;
import com.woxqaq.im.core.config.AppConfig;
import com.woxqaq.im.core.utils.ChannelManager;
import io.netty.channel.socket.nio.NioSocketChannel;
import  java.io.IOException;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
public class RouteHandler {
    @Resource
    private OkHttpClient httpClient;

    @Resource
    private AppConfig appConfig;

    @Resource
    private RouterAPI routerAPI;

    /**
     *
     * @param userInfo
     * @param channel
     */
    public  void  userOffLine(UserInfo userInfo, NioSocketChannel channel) {
        if (userInfo != null) {
            log.info("Account {} offline", userInfo.getUsername());
            ChannelManager.removeSession(userInfo.getUserId());
            clearRouteInfo(userInfo);
        }
        ChannelManager.remove(channel);
    }

    /**
     * @param userInfo
     * @throws IOException
     */
    public  void clearRouteInfo(UserInfo userInfo) {
        ChatReq req = new ChatReq(userInfo.getUserId(), userInfo.getUsername(), null);
        routerAPI.offLine(req);
    }
}
