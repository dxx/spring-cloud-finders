package finders.example.discovery.consumer.controller;

import finders.example.discovery.consumer.client.GreetingClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author dxx
 */
@RestController
public class ConsumerController {

    private static final Logger LOG = LoggerFactory.getLogger(ConsumerController.class);

    private final RestTemplate restTemplate;

    private final GreetingClient greetingClient;

    public ConsumerController(RestTemplate restTemplate,
                              GreetingClient greetingClient) {
        this.restTemplate = restTemplate;
        this.greetingClient = greetingClient;
    }

    @GetMapping("/consumer/rest")
    public String greetingByRest() {
        LOG.info("Access /consumer/rest");
        return restTemplate.getForObject("http://greeting-service/hello", String.class);
    }

    @GetMapping("/consumer/feign")
    public String greetingByFeign() {
        LOG.info("Access /consumer/feign");
        return greetingClient.hello();
    }

}
