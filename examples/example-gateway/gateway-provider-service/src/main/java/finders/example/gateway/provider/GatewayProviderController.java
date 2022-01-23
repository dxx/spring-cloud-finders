package finders.example.gateway.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dxx
 */
@RestController
public class GatewayProviderController {

    private static final Logger LOG = LoggerFactory.getLogger(GatewayProviderController.class);

    @Value("${spring.application.name}")
    private String appName;

    @Value("${server.port}")
    private String port;

    @GetMapping("/response/{msg}")
    public String response(@PathVariable String msg) {
        LOG.info("Access /response/" + msg);
        return String.format("Response %s from %s:%s", msg, appName, port);
    }

}
