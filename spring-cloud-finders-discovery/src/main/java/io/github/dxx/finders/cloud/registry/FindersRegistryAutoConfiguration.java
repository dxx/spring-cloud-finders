package io.github.dxx.finders.cloud.registry;

import io.github.dxx.finders.cloud.ConditionalOnFindersDiscoveryEnabled;
import io.github.dxx.finders.cloud.FindersServiceAutoConfiguration;
import io.github.dxx.finders.cloud.FindersDiscoveryProperties;
import io.github.dxx.finders.cloud.FindersServiceManager;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.serviceregistry.AutoServiceRegistrationAutoConfiguration;
import org.springframework.cloud.client.serviceregistry.AutoServiceRegistrationConfiguration;
import org.springframework.cloud.client.serviceregistry.AutoServiceRegistrationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author dxx
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnFindersDiscoveryEnabled
@ConditionalOnProperty(value = "spring.cloud.service-registry.auto-registration.enabled", matchIfMissing = true)
@AutoConfigureAfter({
        AutoServiceRegistrationConfiguration.class,
        AutoServiceRegistrationAutoConfiguration.class,
        FindersServiceAutoConfiguration.class })
public class FindersRegistryAutoConfiguration {

    @Bean
    public FindersServiceRegistry findersServiceRegistry(
            FindersDiscoveryProperties findersDiscoveryProperties,
            FindersServiceManager findersServiceManager) {
        return new FindersServiceRegistry(findersDiscoveryProperties, findersServiceManager);
    }

    @Bean
    @ConditionalOnBean(AutoServiceRegistrationProperties.class)
    public FindersRegistration findersRegistration(FindersDiscoveryProperties findersDiscoveryProperties) {
        return new FindersRegistration(findersDiscoveryProperties);
    }

    @Bean
    @ConditionalOnBean(AutoServiceRegistrationProperties.class)
    public FindersAutoServiceRegistration findersAutoServiceRegistration(
            FindersServiceRegistry findersServiceRegistry,
            FindersRegistration findersRegistration,
            AutoServiceRegistrationProperties autoServiceRegistrationProperties) {
        return new FindersAutoServiceRegistration(findersServiceRegistry, autoServiceRegistrationProperties, findersRegistration);
    }

}
