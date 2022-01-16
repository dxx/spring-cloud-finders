package io.github.dxx.finders.cloud;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.ConditionalOnDiscoveryEnabled;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author dxx
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnDiscoveryEnabled
@ConditionalOnFindersDiscoveryEnabled
@EnableConfigurationProperties({FindersDiscoveryProperties.class})
public class FindersServiceAutoConfiguration {

    @Bean
    public FindersServiceManager findersService(FindersDiscoveryProperties findersDiscoveryProperties) {
        return new FindersServiceManager(findersDiscoveryProperties);
    }

}
