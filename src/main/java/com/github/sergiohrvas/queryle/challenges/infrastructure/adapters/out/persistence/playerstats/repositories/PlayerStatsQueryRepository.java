package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.out.persistence.playerstats.repositories;

import java.util.UUID;

import com.github.sergiohrvas.queryle.challenges.application.queries.getplayerstats.PlayerStatsView;

public interface PlayerStatsQueryRepository {
    public PlayerStatsView getStats(UUID playerId);
}