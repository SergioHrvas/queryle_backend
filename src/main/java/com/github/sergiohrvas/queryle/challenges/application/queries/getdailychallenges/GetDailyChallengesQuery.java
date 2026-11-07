package com.github.sergiohrvas.queryle.challenges.application.queries.getdailychallenges;

import java.time.LocalDate;
import java.util.UUID;

public record GetDailyChallengesQuery(
    UUID playerId,
    String difficulty,
    LocalDate dateFrom,
    LocalDate dateTo
) {
}
