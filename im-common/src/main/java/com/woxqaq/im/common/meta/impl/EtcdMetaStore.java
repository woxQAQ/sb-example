package com.woxqaq.im.common.meta.impl;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.woxqaq.im.common.meta.MetaConfig;
import com.woxqaq.im.common.meta.MetaStore;
import com.woxqaq.im.common.pojo.RouteInfo;
import com.woxqaq.im.common.utils.ParseRouteInfo;

import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.Client;
import io.etcd.jetcd.KV;
import io.etcd.jetcd.KeyValue;
import io.etcd.jetcd.Lease;
import io.etcd.jetcd.Watch;
import io.etcd.jetcd.lease.LeaseKeepAliveResponse;
import io.etcd.jetcd.options.PutOption;
import io.etcd.jetcd.support.Observers;
import io.etcd.jetcd.watch.WatchEvent;
import io.etcd.jetcd.watch.WatchResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EtcdMetaStore implements MetaStore {
    private static final String DEFAULT_KEY = "/server";
    LoadingCache<String, String> cache;

    private Client client;

    @Override
    public void init(MetaConfig<?> config) throws Exception {
        cache = Caffeine.newBuilder()
                .maximumSize(10_000)
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) throws Exception {
                        return null;
                    }
                });
        client = Client.builder()
                .endpoints(config.getMetaServiceUri())
                .connectTimeout(Duration.ofMillis(config.getTimeoutMs()))
                .build();
    }

    @Override
    public Set<String> getAllAvailableServer() throws Exception {
        if (cache.estimatedSize() > 0) {
            return cache.asMap().keySet();
        }
        Map<String, String> coll = client
                .getKVClient()
                .get(fromString("/server"))
                .get().getKvs().stream()
                .map(KeyValue::getValue)
                .map(ByteSequence::toString)
                .collect(Collectors.toMap(Function.identity(), Function.identity()));
        cache.putAll(coll);
        return coll.keySet();
    }

    @Override
    public void addServer(String ip, int port, int httpPort) throws Exception {
        String routeInfo = ParseRouteInfo.parse(RouteInfo
                .builder()
                .ip(ip)
                .port(port)
                .httpPort(httpPort)
                .build());
        MessageDigest.getInstance("md5").digest();
        Lease lease = client.getLeaseClient();
        lease.grant(60).thenAccept(res -> {
            long leaseId = res.getID();

            KV kvClient = client.getKVClient();

            PutOption opt = PutOption
                    .builder().withLeaseId(leaseId)
                    .build();
            kvClient.put(
                    fromString(String.format("/server/%s", routeInfo)),
                    fromString(""), opt)
                    .thenAccept(resp -> {
                        lease.keepAlive(leaseId, Observers.observer(new Consumer<LeaseKeepAliveResponse>() {
                            @Override
                            public void accept(LeaseKeepAliveResponse t) {
                                log.info("keep alive: {}", t);
                            }
                        }));
                    });
        });
    }

    private ByteSequence fromString(String s) {
        return ByteSequence.from(s, StandardCharsets.UTF_8);
    }

    @Override
    public void listenServerList(ChildListener childListener) throws Exception {
        client.getWatchClient().watch(fromString(DEFAULT_KEY), new Watch.Listener() {

            @Override
            public void onNext(WatchResponse response) {
                for (WatchEvent ev : response.getEvents()) {
                    try {
                        List<String> servers = new ArrayList<>();
                        servers.addLast(ev.getKeyValue().getKey().toString());
                        childListener.childChanged(DEFAULT_KEY, servers);
                        rebuildCache();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onError(Throwable throwable) {
                log.error("error:{}", throwable);
            }

            @Override
            public void onCompleted() {
            }
        });
    }

    @Override
    public void rebuildCache() throws Exception {
        cache.invalidateAll();
        this.getAllAvailableServer();
    }

}
