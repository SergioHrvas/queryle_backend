package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.out.persistence.dailychallenge.repositories.dto;

import java.time.LocalDate;
import java.sql.Timestamp;
import java.util.UUID;

public record DailyChallengeDetailRow(
    UUID id,
    String formulation,
    String difficulty,
    LocalDate publicationDate,
    String name,
    String description,
    String schema,
    String publicDataset,
    UUID gameId,
    String gameStatus,
    UUID attemptId,
    String attemptQuery,
    Timestamp submittedAt,
    Boolean validationPassed,
    String columnsNamesMatchLevel,
    String columnsCountMatchLevel,
    String rowsCountMatchLevel,
    String rowsDataMatchLevel,
    String rowsOrderMatchLevel
) {}