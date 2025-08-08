package com.ayursetu.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // User Service Routes
                .route("user-service", r -> r
                        .path("/api/users/**")
                        .filters(f -> f
                                .rewritePath("/api/users/(?<segment>.*)", "/api/users/${segment}")
                                .addRequestHeader("X-Response-Time", System.currentTimeMillis() + ""))
                        .uri("lb://user-service"))
                
                // Doctor Service Routes
                .route("doctor-service", r -> r
                        .path("/api/doctors/**")
                        .and()
                        .method(HttpMethod.GET, HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE, HttpMethod.PATCH)
                        .filters(f -> f
                                .rewritePath("/api/doctors/(?<segment>.*)", "/api/doctors/${segment}")
                                .addRequestHeader("X-Response-Time", System.currentTimeMillis() + ""))
                        .uri("lb://doctor-service"))
                
                // Specialization Routes (also handled by doctor service)
                .route("specialization-service", r -> r
                        .path("/api/specializations/**")
                        .filters(f -> f
                                .rewritePath("/api/specializations/(?<segment>.*)", "/api/specializations/${segment}")
                                .addRequestHeader("X-Response-Time", System.currentTimeMillis() + ""))
                        .uri("lb://doctor-service"))
                
                // Consultation Service Routes
                .route("consultation-service", r -> r
                        .path("/api/consultations/**")
                        .filters(f -> f
                                .rewritePath("/api/consultations/(?<segment>.*)", "/api/consultations/${segment}")
                                .addRequestHeader("X-Response-Time", System.currentTimeMillis() + ""))
                        .uri("lb://consultation-service"))
                
                // Pharmacy Service Routes
                .route("pharmacy-service", r -> r
                        .path("/api/pharmacy/**")
                        .filters(f -> f
                                .rewritePath("/api/pharmacy/(?<segment>.*)", "/api/pharmacy/${segment}")
                                .addRequestHeader("X-Response-Time", System.currentTimeMillis() + ""))
                        .uri("lb://pharmacy-service"))
                
                // Payment Service Routes
                .route("payment-service", r -> r
                        .path("/api/payments/**")
                        .filters(f -> f
                                .rewritePath("/api/payments/(?<segment>.*)", "/api/payments/${segment}")
                                .addRequestHeader("X-Response-Time", System.currentTimeMillis() + ""))
                        .uri("lb://payment-service"))
                
                // Notification Service Routes
                .route("notification-service", r -> r
                        .path("/api/notifications/**")
                        .filters(f -> f
                                .rewritePath("/api/notifications/(?<segment>.*)", "/api/notifications/${segment}")
                                .addRequestHeader("X-Response-Time", System.currentTimeMillis() + ""))
                        .uri("lb://notification-service"))
                
                .build();
    }
}
