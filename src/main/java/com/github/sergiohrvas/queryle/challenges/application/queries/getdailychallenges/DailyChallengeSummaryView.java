package com.github.sergiohrvas.queryle.challenges.application.queries.getdailychallenges;

import java.time.LocalDate;
import java.util.UUID;

public record DailyChallengeSummaryView(
    UUID id,
    String formulation,
    LocalDate publicationDate,
    String status
) {
}