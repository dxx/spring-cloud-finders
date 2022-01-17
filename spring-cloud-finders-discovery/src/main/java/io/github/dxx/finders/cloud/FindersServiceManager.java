package io.github.dxx.finders.cloud;

import io.github.dxx.finders.client.FindersClient;
import io.github.dxx.finders.client.FindersClientConfig;
import io.github.dxx.finders.client.FindersClientService;
import io.github.dxx.finders.client.loadbalance.LoadBalancerType;

import java.util.Objects;

/**
 * @author dxx
 */
public class FindersServiceManager {

    private final FindersDiscoveryProperties discoveryProperties;

    private FindersClientService findersClientService;

    public FindersServiceManager(FindersDiscoveryProperties discoveryProperties) {
        this.discoveryProperties = discoveryProperties;
    }

    public FindersClientService getFindersClientService() {
        if (Objects.isNull(findersClientService)) {
            return buildFindersClientService(discoveryProperties);
        }
        return findersClientService;
    }

    public void close() {
        this.findersClientService.close();
    }

    private FindersClientService buildFindersClientService(FindersDiscoveryProperties discoveryProperties) {
        if (Objects.isNull(findersClientService)) {
            synchronized (FindersServiceManager.class) {
                if (Objects.isNull(findersClientService)) {
                    findersClientService = createFindersClientService(discoveryProperties);
                }
            }
        }
        return findersClientService;
    }

    private FindersClientService createFindersClientService(FindersDiscoveryProperties discoveryProperties) {
        FindersClientConfig.Builder findersClientConfigBuilder = FindersClientConfig.builder()
                .serverList(discoveryProperties.getServerList());
        if (discoveryProperties.getRequestMaxRetry() > 0) {
            findersClientConfigBuilder.requestMaxRetry(discoveryProperties.getRequestMaxRetry());
        }
        if (discoveryProperties.getServicePullThreads() > 0) {
            findersClientConfigBuilder.servicePullThreads(discoveryProperties.getServicePullThreads());
        }
        if (discoveryProperties.getHeartbeatThreads() > 0) {
            findersClientConfigBuilder.heartbeatThreads(discoveryProperties.getHeartbeatThreads());
        }
        if (discoveryProperties.getServicePullPeriod() > 0) {
            findersClientConfigBuilder.servicePullPeriod(discoveryProperties.getServicePullPeriod());
        }
        if (discoveryProperties.getHeartbeatPeriod() > 0) {
            findersClientConfigBuilder.heartbeatPeriod(discoveryProperties.getHeartbeatPeriod());
        }

        return new FindersClient(discoveryProperties.getNamespace(), findersClientConfigBuilder.build(),
                LoadBalancerType.ROUND);
    }
}
