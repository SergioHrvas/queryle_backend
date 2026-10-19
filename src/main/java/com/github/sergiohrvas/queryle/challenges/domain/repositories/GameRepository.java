package com.github.sergiohrvas.queryle.challenges.domain.repositories;

import java.util.Optional;
import java.util.UUID;

import com.github.sergiohrvas.queryle.challenges.domain.models.Game;

public interface GameRepository {
    Game save(Game game);
    Optional<Game> findById(UUID id);
    Optional<Game> findByPlayerIdAndDailyChallengeId(UUID playerId, UUID dailyChallengeId);    
}
