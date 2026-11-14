package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.out.security;

import java.util.Objects;
import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import com.github.sergiohrvas.queryle.challenges.application.ports.CurrentPlayerPort;
import com.github.sergiohrvas.queryle.challenges.application.services.ResolveCurrentPlayerService;

@Component
public class SpringSecurityCurrentPlayerAdapter implements CurrentPlayerPort {

    private final ResolveCurrentPlayerService resolveCurrentPlayerService;

    public SpringSecurityCurrentPlayerAdapter(ResolveCurrentPlayerService resolveCurrentPlayerService) {
        this.resolveCurrentPlayerService = resolveCurrentPlayerService;
    }

    @Override
    public UUID getCurrentPlayerId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (Objects.isNull(authentication) || !authentication.isAuthenticated()) {
            throw new IllegalStateException("No authenticated user found in the security context.");
        }

        JwtAuthenticationToken jwtAuthToken = (JwtAuthenticationToken) authentication;
        String subject = jwtAuthToken.getToken().getSubject();
        String username = jwtAuthToken.getToken().getClaimAsString("preferred_username");
        String email = jwtAuthToken.getToken().getClaimAsString("email");

        System.out.println("Subject: " + subject);
        System.out.println("Username: " + username);
        System.out.println("Email: " + email);
        
        return resolveCurrentPlayerService.resolve(subject, username, email);
    }
}