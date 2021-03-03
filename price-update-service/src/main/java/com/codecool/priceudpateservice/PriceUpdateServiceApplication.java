package com.codecool.priceudpateservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@SpringBootApplication
@EnableScheduling
@EnableEurekaClient
public class PriceUpdateServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PriceUpdateServiceApplication.class, args);
    }

    @Bean
    Executor executor() {
        return Executors.newSingleThreadExecutor();
    }

}
