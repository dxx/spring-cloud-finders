package finders.example.discovery.greeting.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * Created by dxx on 2022-01-23.
 */
@Configuration
@EnableFeignClients(basePackages = { "finders.example.discovery.greeting.client" }) // 启用 Feign 自动代理
public class FeignConfig {
}
