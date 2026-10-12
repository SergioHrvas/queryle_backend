package com.github.sergiohrvas.queryle.challenges.domain.events;

import java.util.UUID;

public record GameCompletedEvent(
    UUID playerId, 
    UUID dailyChallengeId, 
    boolean won
) implements DomainEvent {}