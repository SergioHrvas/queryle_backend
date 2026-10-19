package com.github.sergiohrvas.queryle.challenges.application.ports;

import java.util.UUID;

import com.github.sergiohrvas.queryle.challenges.application.dtos.PlayerStatsReadModel;

public interface PlayerStatsQueryPort {
    PlayerStatsReadModel findByPlayerId(UUID playerId);
}
