package io.github.dxx.finders.cloud.endpoint;

import io.github.dxx.finders.client.FindersClientService;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;

/**
 * @author dxx
 */
public class FindersDiscoveryHealthIndicator extends AbstractHealthIndicator {

    private final FindersClientService findersClientService;

    public FindersDiscoveryHealthIndicator(FindersClientService findersClientService) {
        this.findersClientService = findersClientService;
    }

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        boolean isHealth = findersClientService.serverHealth();
        if (isHealth) {
            builder.up();
            return;
        }
        builder.down();
    }

}
