package com.campusstore.api_gateway.config;

import com.campusstore.api_gateway.filter.JwtAuthFilter;
import com.campusstore.api_gateway.security.JwtValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.uri;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;
import static org.springframework.web.servlet.function.RequestPredicates.path;

@Configuration
public class GatewayRoutesConfig {

    private final JwtValidator jwtValidator;

    public GatewayRoutesConfig(JwtValidator jwtValidator) {
        this.jwtValidator = jwtValidator;
    }

    // no token needed
    @Bean
    public RouterFunction<ServerResponse> authServiceRoute() {
        return route("auth-service")
                .route(path("/auth/**"), http())
                .before(uri("http://localhost:8081"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> productServiceRoute() {
        return route("product-service")
                .route(path("/products/**"), http())
                .before(uri("http://localhost:8082"))
                .filter(JwtAuthFilter.check(jwtValidator))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> orderServiceRoute() {
        return route("order-service")
                .route(path("/orders/**"), http())
                .before(uri("http://localhost:8083"))
                .filter(JwtAuthFilter.check(jwtValidator))
                .build();
    }
}