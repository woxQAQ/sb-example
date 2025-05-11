package com.woxqaq.im.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
        log.info("Start cim server success!!!");
    }

    // @Override
    // public void run(String... args) throws Exception {
    // String addr = InetAddress.getLocalHost().getHostAddress();
    // }
}
