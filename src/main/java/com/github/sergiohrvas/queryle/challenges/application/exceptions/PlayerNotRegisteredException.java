package com.github.sergiohrvas.queryle.challenges.application.exceptions;

import com.github.sergiohrvas.queryle.challenges.domain.models.ExternalIdentity;

public class PlayerNotRegisteredException extends RuntimeException {
    public PlayerNotRegisteredException(ExternalIdentity identity) {
        super("No player is registered for identity " + identity.subject());
    }
}
