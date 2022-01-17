package io.github.dxx.finders.cloud.discovery;

import io.github.dxx.finders.client.FindersClientService;
import io.github.dxx.finders.client.FindersRuntimeException;
import io.github.dxx.finders.client.model.Instance;
import io.github.dxx.finders.cloud.FindersDiscoveryProperties;
import io.github.dxx.finders.cloud.FindersServiceInstance;
import io.github.dxx.finders.cloud.FindersServiceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dxx
 */
public class FindersDiscoveryClient implements DiscoveryClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(FindersDiscoveryClient.class);

    public static final String DESCRIPTION = "Spring Cloud Finders Discovery Client";

    private final FindersDiscoveryProperties findersDiscoveryProperties;

    private final FindersServiceManager findersServiceManager;

    public FindersDiscoveryClient(FindersDiscoveryProperties findersDiscoveryProperties,
                                  FindersServiceManager findersServiceManager) {
        this.findersDiscoveryProperties = findersDiscoveryProperties;
        this.findersServiceManager = findersServiceManager;
    }

    @Override
    public String description() {
        return DESCRIPTION;
    }

    @Override
    public List<ServiceInstance> getInstances(String serviceId) {
        FindersClientService findersClientService = findersServiceManager.getFindersClientService();
        String cluster = findersDiscoveryProperties.getCluster();
        try {
            List<Instance> instances = findersClientService.getInstances(serviceId, cluster, true);
            return instanceToServiceInstances(instances, serviceId);
        } catch (Exception e) {
            throw new FindersRuntimeException(String.format("Can not get instance of %s from finders server", serviceId), e);
        }
    }

    @Override
    public List<String> getServices() {
        LOGGER.error("Get all service name from finders server error");
        return Collections.emptyList();
    }

    private List<ServiceInstance> instanceToServiceInstances(List<Instance> instances, String serviceId) {
        return instances.stream().map(item -> instanceToServiceInstance(item, serviceId)).collect(Collectors.toList());
    }

    private ServiceInstance instanceToServiceInstance(Instance instance, String serviceId) {
        return new FindersServiceInstance(instance.getInstanceId(),
                serviceId, instance.getIp(), instance.getPort(),
                findersDiscoveryProperties.isSecure());
    }

}
