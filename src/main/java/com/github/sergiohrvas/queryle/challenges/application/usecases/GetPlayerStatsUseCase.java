package com.github.sergiohrvas.queryle.challenges.application.usecases;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.github.sergiohrvas.queryle.challenges.domain.models.PlayerStats;
import com.github.sergiohrvas.queryle.challenges.domain.ports.PlayerStatsPort;

@Service
public class GetPlayerStatsUseCase {
    
    private final PlayerStatsPort playerStatsPort;

    public GetPlayerStatsUseCase(PlayerStatsPort playerStatsPort) {
        this.playerStatsPort = playerStatsPort;
    }

    public PlayerStats execute(UUID playerId) {
        return playerStatsPort.findByPlayerId(playerId).orElseGet(() -> new PlayerStats(playerId));
    }
}