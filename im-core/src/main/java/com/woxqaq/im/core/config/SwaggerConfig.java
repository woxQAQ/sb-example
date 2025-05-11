package com.woxqaq.im.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI createRestApi() {
        return new OpenAPI()
                .info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
                .title("im server")
                .description("im server api")
                .contact(contact())
                .version("0.0.1-alpha.0");
    }

    private Contact contact() {
        return new Contact()
                .email("woxqaq@gmail.com")
                .name("woxQAQ");
    }
}
