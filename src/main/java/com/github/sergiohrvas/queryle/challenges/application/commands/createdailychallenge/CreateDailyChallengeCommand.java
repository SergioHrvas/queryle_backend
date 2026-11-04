package com.github.sergiohrvas.queryle.challenges.application.commands.createdailychallenge;

import java.time.LocalDate;
import java.util.UUID;

public record CreateDailyChallengeCommand(
    String formulation,
    String query,
    String difficulty,
    LocalDate publicationDate,
    UUID dataContextId
) { }
