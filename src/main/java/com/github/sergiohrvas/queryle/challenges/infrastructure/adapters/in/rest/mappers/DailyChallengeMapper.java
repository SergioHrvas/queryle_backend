package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.mappers;

import java.util.Objects;

import com.github.sergiohrvas.queryle.challenges.domain.models.DailyChallenge;
import com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.dtos.response.DailyChallengeResponseDTO;

public interface DailyChallengeMapper {
    public static DailyChallengeResponseDTO toDTO(DailyChallenge dailyChallenge) {
        if (Objects.isNull(dailyChallenge)) return null;

        return new DailyChallengeResponseDTO(
            dailyChallenge.getId(),
            dailyChallenge.getFormulation(), 
            dailyChallenge.getDataContextId(),
            dailyChallenge.getSequence()
        );
    }
}