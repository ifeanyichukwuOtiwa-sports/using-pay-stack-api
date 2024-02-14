package io.wtech.usingpaystack.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class AppConfig {

    @Value("${app.paystack.secret-key:secret}")
    private String paystackSecretKey;
    @Value("${app.paystack.read-timeout:3000}")
    private int connectionTimeout;
    @Value("${app.paystack.connection-timeout:3000}")
    private int readTimeOut;


    @Bean
    RestClient restClient() {
        final SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(readTimeOut);
        factory.setConnectTimeout(connectionTimeout);
        return RestClient.builder()
                .baseUrl("https://api.paystack.co/")
                .defaultHeader("Content-type", "application/json")
                .defaultHeader("Authorization", "Bearer " + paystackSecretKey)
                .requestFactory(factory)
                .build();
    }
}
