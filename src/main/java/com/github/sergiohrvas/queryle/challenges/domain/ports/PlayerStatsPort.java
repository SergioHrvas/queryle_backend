package com.github.sergiohrvas.queryle.challenges.domain.ports;

import java.util.Optional;
import java.util.UUID;

import com.github.sergiohrvas.queryle.challenges.domain.models.PlayerStats;

public interface PlayerStatsPort {
    void save(PlayerStats playerStats);
    Optional<PlayerStats> findById(UUID id);
    Optional<PlayerStats> findByUserId(UUID userId);
}
