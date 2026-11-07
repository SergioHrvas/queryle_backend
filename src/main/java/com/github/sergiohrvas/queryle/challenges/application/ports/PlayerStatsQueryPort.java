package com.github.sergiohrvas.queryle.challenges.application.ports;

import java.util.UUID;

import com.github.sergiohrvas.queryle.challenges.application.queries.getplayerstats.PlayerStatsView;

public interface PlayerStatsQueryPort {
    PlayerStatsView findByPlayerId(UUID playerId);
}
