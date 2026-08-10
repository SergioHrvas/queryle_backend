package com.github.sergiohrvas.queryle.challenges.application.dtos;

import java.util.Optional;

import com.github.sergiohrvas.queryle.challenges.domain.models.DailyChallenge;
import com.github.sergiohrvas.queryle.challenges.domain.models.Game;

public record DailyChallengeGameDTO(
    DailyChallenge dailyChallenge,
    Optional<Game> game
) {
}
