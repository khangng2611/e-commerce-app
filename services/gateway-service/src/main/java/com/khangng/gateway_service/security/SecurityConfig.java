package com.khangng.gateway_service.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
    @Value("${keycloak.client-id}")
    private String kcClientId;

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeExchange(exchange -> exchange
                        .pathMatchers("/eureka/**").permitAll()
                        
                        // customer
                        .pathMatchers("/api/v1/customers/me").authenticated()
                        .pathMatchers(HttpMethod.GET, "/api/v1/customers/**").hasRole("ADMIN_WRITE")
                        .pathMatchers(HttpMethod.PUT, "/api/v1/customers/**").hasRole("ADMIN_WRITE")
                        .pathMatchers(HttpMethod.DELETE, "/api/v1/customers/**").hasRole("ADMIN_WRITE")
                        
                        // product & category
                        .pathMatchers(HttpMethod.POST, "/api/v1/products/purchase/**").authenticated()
                        .pathMatchers(HttpMethod.POST, "/api/v1/categories/**").hasRole("ADMIN_WRITE")
                        .pathMatchers(HttpMethod.POST, "/api/v1/products/**").hasRole("ADMIN_WRITE")
                        
                        // order
                        .pathMatchers("/api/v1/orders/me/**").authenticated()
                        .pathMatchers(HttpMethod.GET, "/api/v1/orders/**").hasRole("ADMIN_WRITE")
                        
                        .anyExchange().authenticated()
                
                )
                .oauth2ResourceServer(oauth2 -> oauth2.jwt().jwtAuthenticationConverter(new KeycloakJwtConverter(kcClientId)));
        return http.build();
    }
}
