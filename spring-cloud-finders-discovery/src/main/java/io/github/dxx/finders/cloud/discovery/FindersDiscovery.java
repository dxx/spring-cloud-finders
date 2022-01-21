package io.github.dxx.finders.cloud.discovery;

import io.github.dxx.finders.client.FindersClientService;
import io.github.dxx.finders.client.model.Instance;
import io.github.dxx.finders.cloud.FindersDiscoveryProperties;
import io.github.dxx.finders.cloud.FindersServiceInstance;
import io.github.dxx.finders.cloud.FindersServiceManager;
import org.springframework.cloud.client.ServiceInstance;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dxx
 */
public class FindersDiscovery {

    private final FindersDiscoveryProperties discoveryProperties;

    private final FindersServiceManager serviceManager;

    public FindersDiscovery(FindersDiscoveryProperties discoveryProperties,
                                  FindersServiceManager serviceManager) {
        this.discoveryProperties = discoveryProperties;
        this.serviceManager = serviceManager;
    }

    public List<ServiceInstance> getInstances(String serviceId) {
        FindersClientService findersClientService = serviceManager.getFindersClientService();
        String cluster = discoveryProperties.getCluster();
        List<Instance> instances = findersClientService.getInstances(serviceId, cluster, true);
        return instanceToServiceInstances(instances, serviceId);
    }

    public List<String> getServiceNames() {
        return serviceManager.getFindersClientService().getServiceNames();
    }

    private List<ServiceInstance> instanceToServiceInstances(List<Instance> instances, String serviceId) {
        return instances.stream().map(item -> instanceToServiceInstance(item, serviceId)).collect(Collectors.toList());
    }

    private ServiceInstance instanceToServiceInstance(Instance instance, String serviceId) {
        return new FindersServiceInstance(instance.getInstanceId(),
                serviceId, instance.getIp(), instance.getPort(),
                discoveryProperties.isSecure());
    }

}
