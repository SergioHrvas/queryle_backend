package com.github.sergiohrvas.queryle.challenges.application.ports;

import java.util.UUID;

import com.github.sergiohrvas.queryle.challenges.application.queries.getplayerstats.PlayerStatsReadModel;

public interface PlayerStatsQueryPort {
    PlayerStatsReadModel findByPlayerId(UUID playerId);
}
