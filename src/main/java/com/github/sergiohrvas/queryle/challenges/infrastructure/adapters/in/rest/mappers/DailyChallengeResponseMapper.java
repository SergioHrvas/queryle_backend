package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.mappers;

import com.github.sergiohrvas.queryle.challenges.application.queries.getdailychallenges.DailyChallengeView;
import com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.dtos.response.DailyChallengeResponseDTO;

public final class DailyChallengeResponseMapper {
    private DailyChallengeResponseMapper() {}

    public static DailyChallengeResponseDTO toDTO(DailyChallengeView dailyChallenge) {
        return new DailyChallengeResponseDTO(
            dailyChallenge.id(),
            dailyChallenge.formulation(),
            dailyChallenge.publicationDate(),
            dailyChallenge.status()
        );
    }
}