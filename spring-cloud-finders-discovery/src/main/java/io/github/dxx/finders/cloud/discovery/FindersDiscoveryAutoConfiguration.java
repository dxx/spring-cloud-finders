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

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnDiscoveryEnabled
    @ConditionalOnBlockingDiscoveryEnabled
    @ConditionalOnFindersDiscoveryEnabled
    @AutoConfigureBefore({
            SimpleDiscoveryClientAutoConfiguration.class,
            CommonsClientAutoConfiguration.class })
    protected static class FindersDiscoveryConfiguration {

        @Bean
        public DiscoveryClient findersDiscoveryClient(
                FindersDiscoveryProperties findersDiscoveryProperties,
                FindersServiceManager findersServiceManager) {
            return new FindersDiscoveryClient(findersDiscoveryProperties, findersServiceManager);
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
        public ReactiveDiscoveryClient findersReactiveDiscoveryClient(
                FindersDiscoveryProperties findersDiscoveryProperties,
                FindersServiceManager findersServiceManager) {
            return new FindersReactiveDiscoveryClient(findersDiscoveryProperties, findersServiceManager);
        }

    }

}
