package com.github.sergiohrvas.queryle.challenges.application.queries;

import java.util.UUID;

public class GetPlayerStatsQuery {
    private final UUID playerId;

    public GetPlayerStatsQuery(UUID playerId) {
        this.playerId = playerId;
    }

    public UUID getPlayerId() {
        return playerId;
    }
}
