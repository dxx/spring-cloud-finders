package io.github.dxx.cloud;

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
public class FindersDiscoveryAutoConfiguration {

    @Bean
    public FindersServiceManager findersService(FindersDiscoveryProperties findersDiscoveryProperties) {
        return new FindersServiceManager(findersDiscoveryProperties);
    }

}
