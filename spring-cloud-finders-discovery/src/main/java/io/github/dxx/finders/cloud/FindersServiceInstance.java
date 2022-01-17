package io.github.dxx.finders.cloud;

import org.springframework.cloud.client.DefaultServiceInstance;

/**
 * @author dxx
 */
public class FindersServiceInstance extends DefaultServiceInstance {

    public FindersServiceInstance() {}

    public FindersServiceInstance(String instanceId, String serviceId,
                                  String host, int port, boolean secure) {
        super(instanceId, serviceId, host, port, secure);
    }

}
