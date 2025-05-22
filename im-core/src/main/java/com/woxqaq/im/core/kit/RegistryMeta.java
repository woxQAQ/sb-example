package com.woxqaq.im.core.kit;

import com.woxqaq.im.common.meta.MetaStore;
import com.woxqaq.im.common.meta.impl.EtcdMetaConfig;
import com.woxqaq.im.core.config.AppConfig;
import com.woxqaq.im.core.utils.SpringBeanFactory;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.retry.ExponentialBackoffRetry;

@Slf4j
public class RegistryMeta implements  Runnable{
    private AppConfig appConfig;

    private MetaStore metaStore;

    private String ip;

    private int imServerPort;
    private int httpPort;

    public RegistryMeta(MetaStore metaStore, String ip, int imServerPort, int httpPort) {
        this.metaStore = metaStore;
        this.ip = ip;
        this.imServerPort = imServerPort;
        this.httpPort = httpPort;
        appConfig = SpringBeanFactory.getBean(AppConfig.class);
    }

    @Override
    @SneakyThrows
    public void run() {
        if(!appConfig.getMetaswitch()) {
            log.info("Skip Registry to metaStore due to application properity");
            return;
        }

        ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, 3);
        metaStore.init(EtcdMetaConfig
                .builder()
                .metaServiceUri(appConfig.getMetaUri())
                .timeoutMs(appConfig.getMetaTimeout())
                .retryPolicy(retryPolicy)
                .build());
        metaStore.addServer(ip, imServerPort, httpPort);
    }
}
