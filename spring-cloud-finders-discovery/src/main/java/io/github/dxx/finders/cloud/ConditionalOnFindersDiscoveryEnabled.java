package io.github.dxx.finders.cloud;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

/**
 * @author dxx
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
@ConditionalOnProperty(value = "spring.cloud.finders.discovery.enabled", matchIfMissing = true)
public @interface ConditionalOnFindersDiscoveryEnabled {
}
