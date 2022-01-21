package io.github.dxx.finders.cloud.discovery;

import io.github.dxx.finders.cloud.ConditionalOnFindersDiscoveryEnabled;
import io.github.dxx.finders.cloud.FindersDiscoveryProperties;
import io.github.dxx.finders.cloud.FindersServiceManager;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.cloud.client.*;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.cloud.client.discovery.composite.reactive.ReactiveCompositeDiscoveryClientAutoConfiguration;
import org.springframework.cloud.client.discovery.simple.SimpleDiscoveryClientAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author dxx
 */
@Configuration(proxyBeanMethods = false)
public class FindersDiscoveryAutoConfiguration {

    @Bean
    public FindersDiscovery findersDiscovery(
            FindersDiscoveryProperties findersDiscoveryProperties,
            FindersServiceManager findersServiceManager) {
        return new FindersDiscovery(findersDiscoveryProperties, findersServiceManager);
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnDiscoveryEnabled
    @ConditionalOnBlockingDiscoveryEnabled
    @ConditionalOnFindersDiscoveryEnabled
    @AutoConfigureBefore({
            SimpleDiscoveryClientAutoConfiguration.class,
            CommonsClientAutoConfiguration.class })
    protected static class FindersDiscoveryConfiguration {

        @Bean
        public DiscoveryClient findersDiscoveryClient(FindersDiscovery findersDiscovery) {
            return new FindersDiscoveryClient(findersDiscovery);
        }

    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnDiscoveryEnabled
    @ConditionalOnReactiveDiscoveryEnabled
    @ConditionalOnFindersDiscoveryEnabled
    @AutoConfigureAfter({ ReactiveCompositeDiscoveryClientAutoConfiguration.class })
    @AutoConfigureBefore({ ReactiveCommonsClientAutoConfiguration.class })
    protected static class FindersReactiveDiscoveryConfiguration {

        @Bean
        public ReactiveDiscoveryClient findersReactiveDiscoveryClient(FindersDiscovery findersDiscovery) {
            return new FindersReactiveDiscoveryClient(findersDiscovery);
        }

    }

}
