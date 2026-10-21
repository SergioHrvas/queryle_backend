package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.mappers;

import com.github.sergiohrvas.queryle.challenges.application.queries.getdailychallenges.DailyChallengeSummaryView;
import com.github.sergiohrvas.queryle.challenges.application.queries.getdailychallenge.DailyChallengeAttemptView;
import com.github.sergiohrvas.queryle.challenges.application.queries.getdailychallenge.DailyChallengeDetailView;
import com.github.sergiohrvas.queryle.challenges.application.queries.getdailychallenge.DailyChallengeGameView;
import com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.dtos.response.DailyChallengeAttemptResponseDTO;
import com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.dtos.response.DailyChallengeDetailResponseDTO;
import com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.dtos.response.DailyChallengeGameResponseDTO;
import com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.dtos.response.DailyChallengeSummaryResponseDTO;

public final class DailyChallengeResponseMapper {
    private DailyChallengeResponseMapper() {}

    public static DailyChallengeSummaryResponseDTO toDTO(DailyChallengeSummaryView dailyChallenge) {
        return new DailyChallengeSummaryResponseDTO(
            dailyChallenge.id(),
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
            dailyChallenge.name(),
            dailyChallenge.description(),
            dailyChallenge.schema(),
            dailyChallenge.publicDataset()
        );
    }

    private static DailyChallengeGameResponseDTO toDTO(DailyChallengeGameView game) {
        if (game == null) {
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