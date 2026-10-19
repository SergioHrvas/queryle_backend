package com.github.sergiohrvas.queryle.challenges.application.queries;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.github.sergiohrvas.queryle.challenges.application.dtos.PlayerStatsReadModel;
import com.github.sergiohrvas.queryle.challenges.application.ports.PlayerStatsQueryPort;

@Service
public class GetPlayerStatsQueryHandler {

    private final PlayerStatsQueryPort playerStatsQueryPort;

    public GetPlayerStatsQueryHandler(PlayerStatsQueryPort playerStatsQueryPort) {
        this.playerStatsQueryPort = playerStatsQueryPort;
    }

    public PlayerStatsReadModel handle(UUID playerId) {
        return playerStatsQueryPort.findByPlayerId(playerId);
    }
}