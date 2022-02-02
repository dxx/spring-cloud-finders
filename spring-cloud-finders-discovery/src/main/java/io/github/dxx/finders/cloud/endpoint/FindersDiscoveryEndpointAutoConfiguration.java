package io.github.dxx.finders.cloud.endpoint;

import io.github.dxx.finders.cloud.ConditionalOnFindersDiscoveryEnabled;
import io.github.dxx.finders.cloud.FindersDiscoveryProperties;
import io.github.dxx.finders.cloud.FindersServiceManager;
import io.github.dxx.finders.cloud.discovery.FindersDiscovery;
import org.springframework.boot.actuate.autoconfigure.endpoint.condition.ConditionalOnAvailableEndpoint;
import org.springframework.boot.actuate.autoconfigure.health.ConditionalOnEnabledHealthIndicator;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author dxx
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(Endpoint.class)
@ConditionalOnFindersDiscoveryEnabled
public class FindersDiscoveryEndpointAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnAvailableEndpoint
    public FindersDiscoveryEndpoint findersDiscoveryEndpoint(
            FindersDiscoveryProperties findersDiscoveryProperties,
            FindersDiscovery findersDiscovery) {
        return new FindersDiscoveryEndpoint(findersDiscoveryProperties, findersDiscovery);
    }

    @Bean
    @ConditionalOnEnabledHealthIndicator("finders-discovery")
    public HealthIndicator findersDiscoveryHealthIndicator(FindersServiceManager findersServiceManager) {
        return new FindersDiscoveryHealthIndicator(findersServiceManager.getFindersClientService());
    }

}
