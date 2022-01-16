package io.github.dxx.cloud.registry;

import io.github.dxx.cloud.FindersDiscoveryProperties;
import io.github.dxx.cloud.FindersServiceManager;
import io.github.dxx.finders.client.FindersClientService;
import io.github.dxx.finders.client.FindersRuntimeException;
import io.github.dxx.finders.client.model.Instance;
import io.github.dxx.finders.client.model.InstanceStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.serviceregistry.ServiceRegistry;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

import static org.springframework.util.ReflectionUtils.rethrowRuntimeException;

/**
 * @author dxx
 */
public class FindersServiceRegistry implements ServiceRegistry<FindersRegistration> {

    private static final Logger LOGGER = LoggerFactory.getLogger(FindersServiceRegistry.class);

    private static final String STATUS_UP = "UP";

    private static final String STATUS_DOWN = "DOWN";

    private final FindersDiscoveryProperties findersDiscoveryProperties;

    private final FindersServiceManager findersServiceManager;

    public FindersServiceRegistry(FindersDiscoveryProperties findersDiscoveryProperties,
                                  FindersServiceManager findersServiceManager) {
        this.findersDiscoveryProperties = findersDiscoveryProperties;
        this.findersServiceManager = findersServiceManager;
    }

    @Override
    public void register(FindersRegistration registration) {
        if (!StringUtils.hasText(registration.getServiceId())) {
            LOGGER.warn("No service to register for finders client");
            return;
        }

        FindersClientService findersClientService = findersServiceManager.getFindersClientService();
        String serviceName = registration.getServiceId();
        String cluster = findersDiscoveryProperties.getCluster();
        try {
            findersClientService.registerInstance(serviceName, registration.getHost(),
                    registration.getPort(), cluster);
            LOGGER.info("Finders register {} {} {}:{} finished", cluster, serviceName,
                    registration.getHost(), registration.getPort());
        }
        catch (Exception e) {
            LOGGER.error(String.format("Finders register %s error", serviceName), e);
            rethrowRuntimeException(e);
        }
    }

    @Override
    public void deregister(FindersRegistration registration) {
        if (!StringUtils.hasText(registration.getServiceId())) {
            LOGGER.warn("No service to deregister for finders client");
            return;
        }

        FindersClientService findersClientService = findersServiceManager.getFindersClientService();
        String serviceName = registration.getServiceId();
        String cluster = findersDiscoveryProperties.getCluster();
        try {
            findersClientService.deregisterInstance(serviceName, registration.getHost(),
                    registration.getPort(), cluster);
        }
        catch (Exception e) {
            LOGGER.error(String.format("Finders deregister %s error", serviceName), e);
        }
        LOGGER.info("Finders deregister finished");
    }

    @Override
    public void close() {
        findersServiceManager.close();
    }

    @Override
    public void setStatus(FindersRegistration registration, String status) {
        if (!STATUS_UP.equalsIgnoreCase(status)
                && !STATUS_DOWN.equalsIgnoreCase(status)) {
            LOGGER.warn("Can't support status {}, please choose UP or DOWN", status);
            return;
        }

        String serviceName = registration.getServiceId();
        Instance instance = getInstanceFromRegistration(registration);
        if (STATUS_DOWN.equalsIgnoreCase(status)) {
            instance.setStatus(InstanceStatus.DISABLE);
        } else {
            instance.setStatus(InstanceStatus.HEALTHY);
        }
        try {
            FindersClientService findersClientService = findersServiceManager.getFindersClientService();
            findersClientService.updateInstance(serviceName, findersDiscoveryProperties.getCluster(), instance);
        } catch (Exception e) {
            throw new FindersRuntimeException(String.format("Update instance of %s status error", serviceName), e);
        }
    }

    @Override
    public String getStatus(FindersRegistration registration) {
        FindersClientService findersClientService = findersServiceManager.getFindersClientService();
        String serviceName = registration.getServiceId();
        try {
            List<Instance> instanceList = findersClientService.getAllInstances(serviceName, findersDiscoveryProperties.getCluster());
            Optional<Instance> optionalInstance = instanceList.stream()
                    .filter(item -> registration.getHost().equals(item.getIp()) && registration.getPort() == item.getPort()).findFirst();
            if (optionalInstance.isPresent()) {
                return optionalInstance.get().getStatus() == InstanceStatus.HEALTHY ? STATUS_UP : STATUS_DOWN;
            }
        } catch (Exception e) {
            LOGGER.error(String.format("Get all instance of %s error", serviceName), e);
        }
        return null;
    }

    private Instance getInstanceFromRegistration(FindersRegistration registration) {
        Instance instance = new Instance();
        instance.setServiceName(registration.getServiceId());
        instance.setCluster(findersDiscoveryProperties.getCluster());
        instance.setIp(registration.getHost());
        instance.setPort(registration.getPort());
        return instance;
    }

}
