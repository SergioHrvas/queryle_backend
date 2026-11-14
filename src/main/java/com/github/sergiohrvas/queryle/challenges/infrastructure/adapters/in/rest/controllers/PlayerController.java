package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.controllers;

import java.util.Objects;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.sergiohrvas.queryle.challenges.application.commands.registerplayer.RegisterPlayerCommand;
import com.github.sergiohrvas.queryle.challenges.application.commands.registerplayer.RegisterPlayerCommandHandler;
import com.github.sergiohrvas.queryle.challenges.domain.models.ExternalIdentity;

@RestController
@RequestMapping("/api/v1/players")
public class PlayerController {
    private final RegisterPlayerCommandHandler registerPlayerCommandHandler;

    public PlayerController(RegisterPlayerCommandHandler registerPlayerCommandHandler) {
        this.registerPlayerCommandHandler = registerPlayerCommandHandler;
    }

    @PutMapping("/me")
    public ResponseEntity<Void> register(@AuthenticationPrincipal Jwt jwt) {
        ExternalIdentity identity = new ExternalIdentity(jwt.getIssuer().toString(), jwt.getSubject());

        registerPlayerCommandHandler.handle(
            new RegisterPlayerCommand(
                identity,
                requiredClaim(jwt, "preferred_username"),
                requiredClaim(jwt, "email")
            )
        );
        return ResponseEntity.noContent().build();
    }

    private String requiredClaim(Jwt jwt, String claim) {
        String value = jwt.getClaimAsString(claim);
        if (Objects.isNull(value) || value.isBlank()) {
            throw new IllegalArgumentException("Required claim is missing: " + claim);
        }
        return value;
    }
}
