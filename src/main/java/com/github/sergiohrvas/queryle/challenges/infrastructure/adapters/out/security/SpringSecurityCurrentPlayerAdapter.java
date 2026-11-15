package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.out.security;

import java.util.Objects;
import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import com.github.sergiohrvas.queryle.challenges.application.ports.CurrentPlayerPort;
import com.github.sergiohrvas.queryle.challenges.application.services.ResolveCurrentPlayerService;
import com.github.sergiohrvas.queryle.challenges.domain.models.ExternalIdentity;

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

        if (!(authentication instanceof JwtAuthenticationToken jwtAuthToken)) {
            throw new IllegalStateException("Authenticated user does not have a JWT identity.");
        }

        String subject = jwtAuthToken.getToken().getSubject();
        String issuer = Objects.toString(jwtAuthToken.getToken().getIssuer(), null);
        return resolveCurrentPlayerService.resolve(new ExternalIdentity(issuer, subject));
    }
}