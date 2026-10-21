package com.github.sergiohrvas.queryle.challenges.application.queries.getdailychallenge;

import java.time.LocalDate;
import java.util.UUID;

public record DailyChallengeDetailView (
    UUID id,
    String formulation,
    String difficulty,
    LocalDate publicationDate,
    DailyChallengeGameView game,
    String name,
    String description,
    String schema,
    String publicDataset
) {

}
