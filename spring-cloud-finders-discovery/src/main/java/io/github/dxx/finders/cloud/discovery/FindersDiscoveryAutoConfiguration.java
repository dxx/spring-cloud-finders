package io.github.dxx.finders.cloud.discovery;

import io.github.dxx.finders.cloud.ConditionalOnFindersDiscoveryEnabled;
import org.springframework.cloud.client.ConditionalOnDiscoveryEnabled;
import org.springframework.context.annotation.Configuration;

/**
 * @author dxx
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnDiscoveryEnabled
@ConditionalOnFindersDiscoveryEnabled
public class FindersDiscoveryAutoConfiguration {
}
