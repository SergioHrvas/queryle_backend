package com.github.sergiohrvas.queryle.challenges.application.queries.getdailychallenges;

import java.time.LocalDate;

public record GetDailyChallengesQuery(
    String difficulty,
    LocalDate dateFrom,
    LocalDate dateTo
) {
}
