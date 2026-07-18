package com.campusstore.api_gateway.filter;

import com.campusstore.api_gateway.security.JwtValidator;
import org.springframework.web.servlet.function.HandlerFilterFunction;
import org.springframework.web.servlet.function.ServerResponse;

public class JwtAuthFilter {

    public static HandlerFilterFunction<ServerResponse, ServerResponse> check(JwtValidator jwtValidator) {
        return (request, next) -> {
            String authHeader = request.headers().firstHeader("Authorization");

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ServerResponse.status(401).body("Missing or invalid Authorization header");
            }

            String token = authHeader.substring(7); // strip "Bearer "

            if (!jwtValidator.isTokenValid(token)) {
                return ServerResponse.status(401).body("Invalid or expired token");
            }

            return next.handle(request);
        };
    }
}