package com.codecool.stockexchange.configuration;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.rsocket.RSocketRequester;

import java.net.URI;

@Configuration
public class BeanConfiguration {

    @Autowired
    private final EurekaClient eurekaClient;

    public BeanConfiguration(EurekaClient eurekaClient) {
        this.eurekaClient = eurekaClient;
    }

    @Bean
    RSocketRequester requester(RSocketRequester.Builder builder) {
        InstanceInfo instanceInfo = eurekaClient.getNextServerFromEureka("apiservice", false);
        String url = "http://" + instanceInfo.getIPAddr() + ":" + instanceInfo.getPort() + "/rsocket";
        return builder.websocket(URI.create(url));
    }

}
