package com.cg.dealscoupons.gatewayservice.config;

import com.cg.dealscoupons.gatewayservice.filter.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringCloudConfig {

    @Autowired
    private JwtAuthenticationFilter filter;

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("auth-service", r -> r.path("/auth/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://auth-service"))
                .route("coupon-service", r -> r.path("/coupon/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://coupon-service"))
                .route("user-service", r -> r.path("/user/**")
                        .uri("lb://user-service"))
                .route("cashback-service", r -> r.path("/wallet/**", "/cashback/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://cashback-service"))
                .build();
    }
}
