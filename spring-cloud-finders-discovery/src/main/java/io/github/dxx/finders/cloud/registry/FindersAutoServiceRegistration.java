package io.github.dxx.finders.cloud.registry;

import org.springframework.cloud.client.serviceregistry.AbstractAutoServiceRegistration;
import org.springframework.cloud.client.serviceregistry.AutoServiceRegistrationProperties;
import org.springframework.cloud.client.serviceregistry.ServiceRegistry;
import org.springframework.util.Assert;

/**
 * @author dxx
 */
public class FindersAutoServiceRegistration extends AbstractAutoServiceRegistration<FindersRegistration> {

    private final FindersRegistration findersRegistration;

    protected FindersAutoServiceRegistration(ServiceRegistry<FindersRegistration> serviceRegistry,
                                             AutoServiceRegistrationProperties properties,
                                             FindersRegistration findersRegistration) {
        super(serviceRegistry, properties);
        this.findersRegistration = findersRegistration;
    }

    @Override
    protected Object getConfiguration() {
        return findersRegistration.getFindersDiscoveryProperties();
    }

    @Override
    protected boolean isEnabled() {
        return findersRegistration.getFindersDiscoveryProperties().isRegisterEnabled();
    }

    @Override
    protected FindersRegistration getRegistration() {
        if (findersRegistration.getPort() < 0 && getPort().get() > 0) {
            findersRegistration.setPort(getPort().get());
        }
        Assert.isTrue(findersRegistration.getPort() > 0, "service.port has not been set");
        return findersRegistration;
    }

    @Override
    protected void register() {
        if (findersRegistration.getPort() < 0) {
            findersRegistration.setPort(getPort().get());
        }
        super.register();
    }

    @Override
    protected FindersRegistration getManagementRegistration() {
        return null;
    }

}
