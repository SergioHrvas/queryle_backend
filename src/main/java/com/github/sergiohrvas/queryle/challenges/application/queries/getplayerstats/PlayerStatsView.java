package com.github.sergiohrvas.queryle.challenges.application.queries.getplayerstats;

import java.util.UUID;

public record PlayerStatsView (
    UUID playerId,
    int playedGames,
    int wonGames,
    int lostGames,
    int currentStreak,
    int longestStreak
){ }