package com.woxqaq.im.router;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class App implements CommandLineRunner {
    public static void main(String[] args) {
        log.info("Start cim router success!!!");
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
    }
}
