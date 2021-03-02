package com.codecool.stockexchange.configuration;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.web.client.RestTemplate;

@org.springframework.context.annotation.Configuration
public class BeanConfiguration {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    RSocketRequester requester(RSocketRequester.Builder builder) {
        return builder.connectTcp("apiservice", 8092).block();
    }

}
