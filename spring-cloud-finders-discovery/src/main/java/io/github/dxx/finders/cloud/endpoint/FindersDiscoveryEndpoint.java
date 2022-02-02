package io.github.dxx.finders.cloud.endpoint;

import io.github.dxx.finders.cloud.FindersDiscoveryProperties;
import io.github.dxx.finders.cloud.discovery.FindersDiscovery;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.cloud.client.ServiceInstance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dxx
 */
@Endpoint(id = "finders-discovery")
public class FindersDiscoveryEndpoint {

    private final FindersDiscoveryProperties discoveryProperties;

    private final FindersDiscovery findersDiscovery;

    public FindersDiscoveryEndpoint(FindersDiscoveryProperties discoveryProperties,
                                    FindersDiscovery findersDiscovery) {
        this.discoveryProperties = discoveryProperties;
        this.findersDiscovery = findersDiscovery;
    }

    @ReadOperation
    public Map<String, Object> findersDiscovery() {
        Map<String, Object> result = new HashMap<>();
        result.put("discoveryProperties", discoveryProperties);

        List<String> serviceNames = findersDiscovery.getServiceNames();
        Map<String, List<ServiceInstance>> map = new HashMap<>();
        serviceNames.forEach(serviceName -> map.put(serviceName, findersDiscovery.getInstances(serviceName)));

        result.put("serviceInstance", map);

        return result;
    }
}
