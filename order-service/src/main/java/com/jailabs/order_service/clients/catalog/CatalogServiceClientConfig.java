package com.jailabs.order_service.clients.catalog;

import java.time.Duration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CatalogServiceClientConfig {

    @Bean
    RestTemplate restTemplate() {
        // Create a SimpleClientHttpRequestFactory to configure timeouts
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout((int) Duration.ofSeconds(5).toMillis());
        factory.setReadTimeout((int) Duration.ofSeconds(5).toMillis());

        // Create and configure RestTemplate
        RestTemplate restTemplate = new RestTemplate(factory);

        // Set the base URL for the RestTemplate
        restTemplate.setRequestFactory(factory);

        return restTemplate;
    }
}
