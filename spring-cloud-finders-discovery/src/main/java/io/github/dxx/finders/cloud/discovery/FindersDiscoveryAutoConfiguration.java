package io.github.dxx.finders.cloud.discovery;

import io.github.dxx.finders.cloud.ConditionalOnFindersDiscoveryEnabled;
import io.github.dxx.finders.cloud.FindersDiscoveryProperties;
import io.github.dxx.finders.cloud.FindersServiceManager;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.cloud.client.CommonsClientAutoConfiguration;
import org.springframework.cloud.client.ConditionalOnBlockingDiscoveryEnabled;
import org.springframework.cloud.client.ConditionalOnDiscoveryEnabled;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.simple.SimpleDiscoveryClientAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author dxx
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnDiscoveryEnabled
@ConditionalOnBlockingDiscoveryEnabled
@ConditionalOnFindersDiscoveryEnabled
@AutoConfigureBefore({
        SimpleDiscoveryClientAutoConfiguration.class,
        CommonsClientAutoConfiguration.class })
public class FindersDiscoveryAutoConfiguration {

    @Bean
    public DiscoveryClient findersDiscoveryClient(
            FindersDiscoveryProperties findersDiscoveryProperties,
            FindersServiceManager findersServiceManager) {
        return new FindersDiscoveryClient(findersDiscoveryProperties, findersServiceManager);
    }

}
