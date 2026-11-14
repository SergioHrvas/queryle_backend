package com.github.sergiohrvas.queryle.challenges.application.commands.registerplayer;

import com.github.sergiohrvas.queryle.challenges.domain.models.ExternalIdentity;

public record RegisterPlayerCommand(
    ExternalIdentity identity,
    String username,
    String email
) {}
