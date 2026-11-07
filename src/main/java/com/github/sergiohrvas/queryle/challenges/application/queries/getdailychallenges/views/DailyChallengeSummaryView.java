package com.github.sergiohrvas.queryle.challenges.application.queries.getdailychallenges.views;

import java.time.LocalDate;
import java.util.UUID;

public record DailyChallengeSummaryView(
    UUID id,
    String formulation,
    String difficulty,
    LocalDate publicationDate,
    String status
) {
}