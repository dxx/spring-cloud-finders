package io.github.dxx.finders.cloud.discovery;

import io.github.dxx.finders.client.FindersClientService;
import io.github.dxx.finders.client.model.Instance;
import io.github.dxx.finders.cloud.FindersDiscoveryProperties;
import io.github.dxx.finders.cloud.FindersServiceInstance;
import io.github.dxx.finders.cloud.FindersServiceManager;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author dxx
 */
public class FindersReactiveDiscoveryClient implements ReactiveDiscoveryClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(FindersReactiveDiscoveryClient.class);

    public static final String DESCRIPTION = "Spring Cloud Finders Reactive Discovery Client";

    private final FindersDiscoveryProperties discoveryProperties;

    private final FindersServiceManager serviceManager;

    public FindersReactiveDiscoveryClient(FindersDiscoveryProperties discoveryProperties,
                                          FindersServiceManager serviceManager) {
        this.discoveryProperties = discoveryProperties;
        this.serviceManager = serviceManager;
    }

    @Override
    public String description() {
        return DESCRIPTION;
    }

    @Override
    public Flux<ServiceInstance> getInstances(String serviceId) {
        return Mono.justOrEmpty(serviceId).flatMapMany(getInstancesFromFinders())
                .subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Flux<String> getServices() {
        return Flux.defer(() -> {
            try {
                return Flux.fromIterable(serviceManager.getFindersClientService().getServiceNames());
            } catch (Exception e) {
                LOGGER.error("Get all service name from finders server failed");
                return Flux.empty();
            }
        }).subscribeOn(Schedulers.boundedElastic());
    }

    private Function<String, Publisher<ServiceInstance>> getInstancesFromFinders() {
        return serviceId -> {
            FindersClientService findersClientService = serviceManager.getFindersClientService();
            String cluster = discoveryProperties.getCluster();
            try {
                List<Instance> instances = findersClientService.getInstances(serviceId, cluster, true);
                return Flux.fromIterable(instanceToServiceInstances(instances, serviceId));
            } catch (Exception e) {
                LOGGER.error(String.format("Can not get instance of %s from finders server", serviceId));
                return Flux.empty();
            }
        };
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
