package finders.example.discovery.greeting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author dxx
 */
@SpringBootApplication
@EnableDiscoveryClient
public class GreetingApplication {

    public static void main(String[] args) {
        SpringApplication.run(GreetingApplication.class);
    }

}
