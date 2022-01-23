package finders.example.discovery.consumer.reactive.controller;

import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author dxx
 */
@RestController
public class ConsumerReactiveController {

    private final ReactiveDiscoveryClient reactiveDiscoveryClient;

    private final WebClient.Builder webClientBuilder;

    public ConsumerReactiveController(ReactiveDiscoveryClient reactiveDiscoveryClient,
                                      WebClient.Builder webClientBuilder) {
        this.reactiveDiscoveryClient = reactiveDiscoveryClient;
        this.webClientBuilder = webClientBuilder;
    }

    @GetMapping("/consumer-reactive/greeting")
    public Mono<String> greeting() {
        return webClientBuilder.build().get()
                .uri("http://greeting-service/hello").retrieve()
                .bodyToMono(String.class);
    }

    @GetMapping("/consumer-reactive/instances")
    public Flux<String> instances() {
        return reactiveDiscoveryClient.getInstances("greeting-service")
                .map(serviceInstance -> serviceInstance.getHost() + ":" + serviceInstance.getPort() + " ");
    }

}
