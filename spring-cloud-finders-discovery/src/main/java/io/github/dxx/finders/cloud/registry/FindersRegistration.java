package io.github.dxx.finders.cloud.registry;

import io.github.dxx.finders.cloud.FindersDiscoveryProperties;
import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.serviceregistry.Registration;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dxx
 */
public class FindersRegistration implements Registration {

    private final FindersDiscoveryProperties findersDiscoveryProperties;

    public FindersRegistration(FindersDiscoveryProperties findersDiscoveryProperties) {
        this.findersDiscoveryProperties = findersDiscoveryProperties;
    }

    @Override
    public String getServiceId() {
        return findersDiscoveryProperties.getService();
    }

    @Override
    public String getHost() {
        return findersDiscoveryProperties.getIp();
    }

    @Override
    public int getPort() {
        return findersDiscoveryProperties.getPort();
    }

    public void setPort(int port) {
        findersDiscoveryProperties.setPort(port);
    }

    @Override
    public boolean isSecure() {
        return findersDiscoveryProperties.isSecure();
    }

    @Override
    public URI getUri() {
        return DefaultServiceInstance.getUri(this);
    }

    @Override
    public Map<String, String> getMetadata() {
        return new HashMap<>();
    }

    public FindersDiscoveryProperties getFindersDiscoveryProperties() {
        return findersDiscoveryProperties;
    }

}
