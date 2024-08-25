package com.khangng.gateway_service.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;

public class KeycloakJwtConverter implements Converter<Jwt, Mono<JwtAuthenticationToken>> {

    private static final String CLAIM_REALM_ACCESS = "realm_access";
    private static final String CLAIM_RESOURCE_ACCESS = "resource_access";
    private static final String CLAIM_ROLES = "roles";
    
    private final String kcClientId;
    
    public KeycloakJwtConverter(String kcClientId) {
        this.kcClientId = kcClientId;
    }
    
    @Override
    public Mono<JwtAuthenticationToken> convert(Jwt jwt) {
        // Extract authorities from JWT
        Collection<GrantedAuthority> grantedAuthorities = extractAuthorities(jwt);
        
        // Return the authentication token wrapped in a Mono
        return Mono.just(new JwtAuthenticationToken(jwt, grantedAuthorities));
    }
    
    public Collection<GrantedAuthority> extractAuthorities (Jwt jwt) {
        Map<String, Collection<String>> realmAccess = jwt.getClaim(CLAIM_REALM_ACCESS);
        Map<String, Map<String, Collection<String>>> resourceAccess = jwt.getClaim(CLAIM_RESOURCE_ACCESS);
        
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        
        if (realmAccess != null && !realmAccess.isEmpty()) {
            Collection<String> realmRole = realmAccess.get(CLAIM_ROLES);
            if (realmRole != null && !realmRole.isEmpty()) {
                realmRole.forEach(r -> {
                    if (resourceAccess != null && !resourceAccess.isEmpty() && resourceAccess.containsKey(kcClientId)) {
                        resourceAccess.get(kcClientId).get(CLAIM_ROLES).forEach(resourceRole -> {
                            String role = String.format("ROLE_%s_%s", r, resourceRole).toUpperCase(Locale.ROOT);
                            grantedAuthorities.add(new SimpleGrantedAuthority(role));
                        });
                    } else {
                        grantedAuthorities.add(new SimpleGrantedAuthority(r));
                    }
                });
            }
        }
        
        return grantedAuthorities;
    }
}
