package io.github.dxx.cloud;

import io.github.dxx.finders.client.constant.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;

/**
 * @author dxx
 */
@ConfigurationProperties("spring.cloud.finders.discovery")
public class FindersDiscoveryProperties {

    /**
     * Url list of finders server.
     */
    private String serverList = "localhost:9080";

    /**
     * Whether to enable service registration.
     */
    private boolean registerEnabled = true;

    /**
     * Namespace of service.
     */
    private String namespace = Services.DEFAULT_NAMESPACE;

    /**
     * Cluster name.
     */
    private String cluster = Services.DEFAULT_CLUSTER;

    /**
     * Service name to registry.
     */
    @Value("${spring.cloud.finders.discovery.service:${spring.application.name:}}")
    private String service;

    /**
     * The ip to register, needn't set it if the autodetect ip works well.
     */
    private String ip;

    /**
     * The port to register, needn't set it if the autodetect port works well.
     */
    private int port = -1;

    /**
     * Whether the service is a https service.
     */
    private boolean secure = false;

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
     * Period of the service pull.
     */
    private long servicePullPeriod;

    /**
     * Period of the heartbeat.
     */
    private long heartbeatPeriod;

    @Autowired
    private InetUtils inetUtils;

    @PostConstruct
    public void init() {
        if (!StringUtils.hasText(ip)) {
            ip = inetUtils.findFirstNonLoopbackHostInfo().getIpAddress();
        }
    }

    public String getServerList() {
        return serverList;
    }

    public void setServerList(String serverList) {
        this.serverList = serverList;
    }

    public boolean isRegisterEnabled() {
        return registerEnabled;
    }

    public void setRegisterEnabled(boolean registerEnabled) {
        this.registerEnabled = registerEnabled;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getCluster() {
        return cluster;
    }

    public void setCluster(String cluster) {
        this.cluster = cluster;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isSecure() {
        return secure;
    }

    public void setSecure(boolean secure) {
        this.secure = secure;
    }

    public int getRequestMaxRetry() {
        return requestMaxRetry;
    }

    public void setRequestMaxRetry(int requestMaxRetry) {
        this.requestMaxRetry = requestMaxRetry;
    }

    public int getServicePullThreads() {
        return servicePullThreads;
    }

    public void setServicePullThreads(int servicePullThreads) {
        this.servicePullThreads = servicePullThreads;
    }

    public int getHeartbeatThreads() {
        return heartbeatThreads;
    }

    public void setHeartbeatThreads(int heartbeatThreads) {
        this.heartbeatThreads = heartbeatThreads;
    }

    public long getServicePullPeriod() {
        return servicePullPeriod;
    }

    public void setServicePullPeriod(long servicePullPeriod) {
        this.servicePullPeriod = servicePullPeriod;
    }

    public long getHeartbeatPeriod() {
        return heartbeatPeriod;
    }

    public void setHeartbeatPeriod(long heartbeatPeriod) {
        this.heartbeatPeriod = heartbeatPeriod;
    }
}
