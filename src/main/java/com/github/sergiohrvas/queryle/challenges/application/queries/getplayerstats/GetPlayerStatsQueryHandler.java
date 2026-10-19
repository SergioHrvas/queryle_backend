package com.github.sergiohrvas.queryle.challenges.application.queries.getplayerstats;

import org.springframework.stereotype.Service;

import com.github.sergiohrvas.queryle.challenges.application.ports.PlayerStatsQueryPort;

@Service
public class GetPlayerStatsQueryHandler {

    private final PlayerStatsQueryPort playerStatsQueryPort;

    public GetPlayerStatsQueryHandler(PlayerStatsQueryPort playerStatsQueryPort) {
        this.playerStatsQueryPort = playerStatsQueryPort;
    }

    public PlayerStatsReadModel handle(GetPlayerStatsQuery query) {
        return playerStatsQueryPort.findByPlayerId(query.getPlayerId());
    }
}