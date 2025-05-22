package com.woxqaq.im.router.config;

import java.lang.reflect.Method;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.woxqaq.im.api.RouterAPI;
import com.woxqaq.im.common.core.proxy.RpcProxyManager;
import com.woxqaq.im.common.meta.MetaStore;
import com.woxqaq.im.common.meta.impl.EtcdMetaConfig;
import com.woxqaq.im.common.meta.impl.EtcdMetaStore;
import com.woxqaq.im.common.pojo.UserInfo;
import com.woxqaq.im.common.route.RouteHandler;
import com.woxqaq.im.common.route.algo.consistentHash.AbstractConsistentHash;
import com.woxqaq.im.router.constants.Constants;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;

@Configuration
@Slf4j
public class BeanConfig {

    @Resource
    private AppConfig appConfig;

    @Bean
    public RouterAPI routerAPI(OkHttpClient httpClient) {
        return RpcProxyManager.create(RouterAPI.class, httpClient);
    }

    @Bean
    public MetaStore metaStore() throws Exception {
        MetaStore store = new EtcdMetaStore();
        ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, 3);
        store.init(EtcdMetaConfig
                .builder()
                .metaServiceUri(appConfig.getMetaUri())
                .timeoutMs(appConfig.getMetaTimeout())
                .retryPolicy(retryPolicy)
                .build());
        store.listenServerList((root, child) -> {
            log.info("server list change, root: {}, child: {}", root, child);
        });
        return store;
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, String> redisTemplate = new StringRedisTemplate(factory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean
    public OkHttpClient okHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();
        return builder.build();
    }

    public RouteHandler routeHandler() throws Exception {
        String routeway = appConfig.getRouteWay();

        RouteHandler routeHandler = (RouteHandler) Class.forName(routeway)
                .getConstructor()
                .newInstance();
        log.info("current route algorithm is {}", routeHandler.getClass().getSimpleName());
        if (routeway.contains("ConsistentHash")) {
            Method method = Class
                    .forName(routeway)
                    .getMethod("setHash", AbstractConsistentHash.class);
            AbstractConsistentHash hash = (AbstractConsistentHash) Class.forName(appConfig.getConsistentHashWay())
                    .getConstructor()
                    .newInstance();
            method.invoke(routeHandler, hash);
        }
        return routeHandler;
    }

    @Bean(value = "userInfoCache")
    public LoadingCache<Long, Optional<UserInfo>> userInfoCache(RedisTemplate<String, String> redisTemplate) {
        return Caffeine.newBuilder()
                .initialCapacity(64)
                .maximumSize(1024)
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .build(new CacheLoader<Long, Optional<UserInfo>>() {
                    @Override
                    public Optional<UserInfo> load(Long key) throws Exception {
                        String sendUserName = redisTemplate.opsForValue().get(Constants.ACCOUNT_PREFIX + key);
                        if (sendUserName == null) {
                            return Optional.empty();
                        }
                        UserInfo info = new UserInfo(key, sendUserName);
                        return Optional.of(info);
                    }
                });
    }
}
