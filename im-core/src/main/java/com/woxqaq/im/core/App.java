package com.woxqaq.im.core;

import com.woxqaq.im.common.meta.MetaStore;
import com.woxqaq.im.core.config.AppConfig;
import com.woxqaq.im.core.kit.RegistryMeta;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.net.InetAddress;

@Slf4j
@SpringBootApplication
public class App implements CommandLineRunner {
    @Resource
    private AppConfig appConfig;

    @Resource
    private MetaStore metaStore;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
        log.info("Start cim server success!!!");
    }

    @Override
    public void run(String... args) throws Exception {
        String addr = InetAddress.getLocalHost().getHostAddress();
        Thread thread = new Thread(new RegistryMeta(metaStore,addr, appConfig.getImPort(),appConfig.getPort()));
        thread.setName("registry-meta");
        thread.start();
    }
}
