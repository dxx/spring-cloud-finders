package io.github.dxx.finders.cloud.discovery;

import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.function.Function;

/**
 * @author dxx
 */
public class FindersReactiveDiscoveryClient implements ReactiveDiscoveryClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(FindersReactiveDiscoveryClient.class);

    public static final String DESCRIPTION = "Spring Cloud Finders Reactive Discovery Client";

    private final FindersDiscovery discovery;

    public FindersReactiveDiscoveryClient(FindersDiscovery discovery) {
        this.discovery = discovery;
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
                return Flux.fromIterable(discovery.getServiceNames());
            } catch (Exception e) {
                LOGGER.error("Get all service name from finders server failed");
                return Flux.empty();
            }
        }).subscribeOn(Schedulers.boundedElastic());
    }

    private Function<String, Publisher<ServiceInstance>> getInstancesFromFinders() {
        return serviceId -> {
            try {
                return Flux.fromIterable(discovery.getInstances(serviceId));
            } catch (Exception e) {
                LOGGER.error(String.format("Can not get instance of %s from finders server", serviceId));
                return Flux.empty();
            }
        };
    }

}
