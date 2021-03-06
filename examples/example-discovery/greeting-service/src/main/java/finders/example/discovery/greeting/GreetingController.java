package finders.example.discovery.greeting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dxx
 */
@RestController
public class GreetingController {

    private static final Logger LOG = LoggerFactory.getLogger(GreetingController.class);

    @Value("${spring.application.name}")
    private String appName;

    @Value("${server.port}")
    private String port;

    @GetMapping("/hello")
    public String hello() {
        LOG.info("Access /hello");
        return String.format("Response hello from %s:%s", appName, port);
    }

}
