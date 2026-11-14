package com.github.sergiohrvas.queryle.challenges.application.queries.getplayerstats;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.github.sergiohrvas.queryle.challenges.application.ports.CurrentPlayerPort;
import com.github.sergiohrvas.queryle.challenges.application.ports.PlayerStatsQueryPort;

@Service
public class GetPlayerStatsQueryHandler {

    private final PlayerStatsQueryPort playerStatsQueryPort;
    private final CurrentPlayerPort currentPlayerPort;

    public GetPlayerStatsQueryHandler(PlayerStatsQueryPort playerStatsQueryPort, CurrentPlayerPort currentPlayerPort) {
        this.playerStatsQueryPort = playerStatsQueryPort;
        this.currentPlayerPort = currentPlayerPort;
    }

    public PlayerStatsView handle() {
        UUID playerId = currentPlayerPort.getCurrentPlayerId();
        return playerStatsQueryPort.findByPlayerId(playerId);
    }
}