package finders.example.gateway.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author dxx
 */
@SpringBootApplication
@EnableDiscoveryClient
public class GatewayProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayProviderApplication.class);
    }

}
