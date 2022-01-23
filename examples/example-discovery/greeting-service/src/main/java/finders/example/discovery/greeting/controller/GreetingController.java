package finders.example.discovery.greeting.controller;

import finders.example.discovery.greeting.client.HelloClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author dxx
 */
@RestController
public class GreetingController {

    private static final Logger LOG = LoggerFactory.getLogger(GreetingController.class);

    private final RestTemplate restTemplate;

    private final HelloClient helloClient;

    public GreetingController(RestTemplate restTemplate,
                              HelloClient helloClient) {
        this.restTemplate = restTemplate;
        this.helloClient = helloClient;
    }

    @GetMapping("/greeting/rest")
    public String greetingByRest() {
        LOG.info("Access /greeting/rest");
        return restTemplate.getForObject("http://hello-service/hello", String.class);
    }

    @GetMapping("/greeting/feign")
    public String greetingByFeign() {
        LOG.info("Access /greeting/feign");
        return helloClient.hello();
    }

}
