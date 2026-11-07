package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.mappers;

import java.util.Objects;

import com.github.sergiohrvas.queryle.challenges.application.queries.getdailychallenge.views.DailyChallengeAttemptView;
import com.github.sergiohrvas.queryle.challenges.application.queries.getdailychallenge.views.DailyChallengeDetailView;
import com.github.sergiohrvas.queryle.challenges.application.queries.getdailychallenge.views.DailyChallengeGameView;
import com.github.sergiohrvas.queryle.challenges.application.queries.getdailychallenge.views.DatasetView;
import com.github.sergiohrvas.queryle.challenges.application.queries.getdailychallenge.views.SchemaView;
import com.github.sergiohrvas.queryle.challenges.application.queries.getdailychallenges.views.DailyChallengeSummaryView;
import com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.dtos.response.DailyChallengeAttemptResponseDTO;
import com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.dtos.response.ColumnSchemaResponseDTO;
import com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.dtos.response.DataContextResponseDTO;
import com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.dtos.response.DatasetResponseDTO;
import com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.dtos.response.DatasetTableResponseDTO;
import com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.dtos.response.DailyChallengeDetailResponseDTO;
import com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.dtos.response.DailyChallengeGameResponseDTO;
import com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.dtos.response.DailyChallengeSummaryResponseDTO;
import com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.dtos.response.SchemaResponseDTO;
import com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.dtos.response.TableSchemaResponseDTO;

public final class DailyChallengeResponseMapper {
    private DailyChallengeResponseMapper() {}

    public static DailyChallengeSummaryResponseDTO toDTO(DailyChallengeSummaryView dailyChallenge) {
        return new DailyChallengeSummaryResponseDTO(
            dailyChallenge.id(),
            dailyChallenge.difficulty(),
            dailyChallenge.formulation(),
            dailyChallenge.publicationDate(),
            dailyChallenge.status()
        );
    }

    public static DailyChallengeDetailResponseDTO toDTO(DailyChallengeDetailView dailyChallenge) {
        return new DailyChallengeDetailResponseDTO(
            dailyChallenge.id(),
            dailyChallenge.formulation(),
            dailyChallenge.difficulty(),
            dailyChallenge.publicationDate(),
            toDTO(dailyChallenge.game()),
            new DataContextResponseDTO(
                dailyChallenge.datacontext().name(),
                dailyChallenge.datacontext().description(),
                toDTO(dailyChallenge.datacontext().schema()),
                toDTO(dailyChallenge.datacontext().dataset())
            )
        );
    }

    private static SchemaResponseDTO toDTO(SchemaView schema) {
        return new SchemaResponseDTO(
            schema.tables().stream()
                .map(table -> new TableSchemaResponseDTO(
                    table.name(),
                    table.columns().stream()
                        .map(column -> new ColumnSchemaResponseDTO(
                            column.name(),
                            column.type(),
                            column.isNullable(),
                            column.isPrimaryKey(),
                            column.position()
                        ))
                        .toList()
                ))
                .toList()
        );
    }

    private static DatasetResponseDTO toDTO(DatasetView dataset) {
        return new DatasetResponseDTO(
            dataset.tables().stream()
                .map(table -> new DatasetTableResponseDTO(
                    table.name(),
                    table.columns(),
                    table.rows()
                ))
                .toList()
        );
    }

    private static DailyChallengeGameResponseDTO toDTO(DailyChallengeGameView game) {
        if (Objects.isNull(game)) {
            return null;
        }

        return new DailyChallengeGameResponseDTO(
            game.status(),
            game.attempts().stream()
                .map(DailyChallengeResponseMapper::toDTO)
                .toList()
        );
    }

    private static DailyChallengeAttemptResponseDTO toDTO(DailyChallengeAttemptView attempt) {
        return new DailyChallengeAttemptResponseDTO(
            attempt.id(),
            attempt.query(),
            attempt.submittedAt(),
            attempt.validationPassed(),
            attempt.columnsNamesMatchLevel(),
            attempt.columnsCountMatchLevel(),
            attempt.rowsCountMatchLevel(),
            attempt.rowsDataMatchLevel(),
            attempt.rowsOrderMatchLevel()
        );
    }
}