package io.github.dxx.cloud;

import org.springframework.cloud.client.ConditionalOnDiscoveryEnabled;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author dxx
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnDiscoveryEnabled
@ConditionalOnFindersDiscoveryEnabled
public class FindersDiscoveryAutoConfiguration {

    @Bean
    public FindersService findersService() {
        return new FindersService();
    }

}
