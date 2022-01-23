package finders.example.discovery.greeting.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author dxx
 */
@FeignClient(value = "hello-service")
public interface HelloClient {

    @GetMapping("/hello")
    String hello();

}
