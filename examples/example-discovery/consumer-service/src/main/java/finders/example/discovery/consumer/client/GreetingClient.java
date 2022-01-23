package finders.example.discovery.consumer.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author dxx
 */
@FeignClient(value = "greeting-service")
public interface GreetingClient {

    @GetMapping("/hello")
    String hello();

}
