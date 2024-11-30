package com.jailabs.order_service.clients.catalog;

import com.jailabs.order_service.ApplicationProperties;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class ProductServiceClient {
    private static final Logger log = LoggerFactory.getLogger(ProductServiceClient.class);

    private final RestTemplate restTemplate;
    private final ApplicationProperties properties;

    // Constructor injecting RestTemplate and ApplicationProperties
    public ProductServiceClient(RestTemplate restTemplate, ApplicationProperties properties) {
        this.restTemplate = restTemplate;
        this.properties = properties;
    }

    @CircuitBreaker(name = "catalog-service")
    @Retry(name = "catalog-service", fallbackMethod = "getProductByCodeFallback")
    public Optional<Product> getProductByCode(String code) throws InterruptedException {
        log.info("Fetching product for code: {}", code);

        // Construct the full URL by combining catalogServiceUrl with the endpoint
        String baseUrl = properties.catalogServiceUrl(); // e.g., "https://example.com"
        String url = baseUrl + "/api/products/{code}"; // Complete URL: "https://example.com/api/products/{code}"

        try {
            // sleep method

            // sleepForMillis(6000);
            // Use RestTemplate to make the request
            Product product = restTemplate.getForObject(url, Product.class, code);
            return Optional.ofNullable(product);
        } catch (HttpClientErrorException e) {
            log.error("Error fetching product for code: {}. Status: {}", code, e.getStatusCode());
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error fetching product for code: {}", code, e);
            throw e;
        }
    }

    // Method to sleep for a given number of milliseconds
    public static void sleepForMillis(long millis) throws InterruptedException {
        System.out.println("Sleeping for " + millis + " milliseconds...");
        Thread.sleep(millis);
        System.out.println("Awoke after sleeping for " + millis + " milliseconds.");
    }

    // Fallback method for getProductByCode when the circuit is open or retries fail
    private Optional<Product> getProductByCodeFallback(String code, Throwable throwable) {
        log.warn("Fallback triggered for product code: {}. Error: {}", code, throwable.getMessage());
        // Provide a default response or handle accordingly (e.g., return an empty product or a cached product)
        return Optional.empty();
    }
}
