package com.github.sergiohrvas.queryle.challenges.domain.ports;

import java.util.Optional;
import java.util.UUID;

import com.github.sergiohrvas.queryle.challenges.domain.models.PlayerStats;

public interface PlayerStatsPort {
    PlayerStats save(PlayerStats playerStats);
    Optional<PlayerStats> findByPlayerId(UUID playerId);
}
