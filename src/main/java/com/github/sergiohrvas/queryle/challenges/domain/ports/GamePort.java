package com.github.sergiohrvas.queryle.challenges.domain.ports;

import java.util.Optional;
import java.util.UUID;

import com.github.sergiohrvas.queryle.challenges.domain.models.Game;

public interface GamePort {
    void save(Game game);
    Optional<Game> findById(UUID id);
    Optional<Game> findByPlayerIdAndDailyChallengeId(UUID playerId, UUID dailyChallengeId);    
}
