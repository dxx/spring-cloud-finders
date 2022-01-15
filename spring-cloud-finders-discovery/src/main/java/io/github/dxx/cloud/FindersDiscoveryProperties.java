package io.github.dxx.cloud;

import io.github.dxx.finders.client.constant.Services;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author dxx
 */
@ConfigurationProperties("spring.cloud.finders.discovery")
public class FindersDiscoveryProperties {

    public static final String PREFIX = "spring.cloud.finders.discovery";

    /**
     * Url list of finders server.
     */
    private String serverList;

    /**
     * Namespace.
     */
    private String namespace = Services.DEFAULT_NAMESPACE;

    /**
     * Cluster name.
     */
    private String cluster = Services.DEFAULT_CLUSTER;

    /**
     * Number of max retries when the request fails.
     */
    private int requestMaxRetry;

    /**
     * Number of threads processing the pull service.
     */
    private int servicePullThreads;

    /**
     * Number of threads processing the heartbeat.
     */
    private int heartbeatThreads;

    /**
     * Cycle of the service pull.
     */
    private long servicePullPeriod;

    /**
     * Cycle of the heartbeat.
     */
    private long heartbeatPeriod;

}
