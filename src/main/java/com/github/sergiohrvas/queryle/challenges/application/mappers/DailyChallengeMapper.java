package com.github.sergiohrvas.queryle.challenges.application.mappers;

import java.util.Objects;

import com.github.sergiohrvas.queryle.challenges.application.dtos.DailyChallengeResponseDTO;
import com.github.sergiohrvas.queryle.challenges.domain.models.DailyChallenge;

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