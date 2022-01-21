package io.github.dxx.finders.cloud.discovery;

import io.github.dxx.finders.client.FindersRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import java.util.Collections;
import java.util.List;

/**
 * @author dxx
 */
public class FindersDiscoveryClient implements DiscoveryClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(FindersDiscoveryClient.class);

    public static final String DESCRIPTION = "Spring Cloud Finders Discovery Client";

    private final FindersDiscovery discovery;

    public FindersDiscoveryClient(FindersDiscovery discovery) {
        this.discovery = discovery;
    }

    @Override
    public String description() {
        return DESCRIPTION;
    }

    @Override
    public List<ServiceInstance> getInstances(String serviceId) {
        try {
            return discovery.getInstances(serviceId);
        } catch (Exception e) {
            throw new FindersRuntimeException(String.format("Can not get instance of %s from finders server", serviceId), e);
        }
    }

    @Override
    public List<String> getServices() {
        try {
            return discovery.getServiceNames();
        } catch (Exception e) {
            LOGGER.error("Get all service name from finders server failed");
            return Collections.emptyList();
        }
    }

}
