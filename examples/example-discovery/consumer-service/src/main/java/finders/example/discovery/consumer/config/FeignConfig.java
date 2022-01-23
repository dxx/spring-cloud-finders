package finders.example.discovery.consumer.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * @author dxx
 */
@Configuration
@EnableFeignClients(basePackages = {"finders.example.discovery.consumer.client"}) // 启用 Feign 自动代理
public class FeignConfig {
}
