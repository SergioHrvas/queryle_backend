package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.mappers;

import java.util.Objects;

import com.github.sergiohrvas.queryle.challenges.application.dtos.DailyChallengeGameDTO;
import com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.dtos.response.DailyChallengeGameResponseDTO;

public class DailyChallengeGameResponseMapper {
    public static DailyChallengeGameResponseDTO toDTO(DailyChallengeGameDTO dailyChallengeGameDTO) {
        if (Objects.isNull(dailyChallengeGameDTO)) return null;

        return new DailyChallengeGameResponseDTO(
            DailyChallengeResponseMapper.toDTO(dailyChallengeGameDTO.dailyChallenge()),
            dailyChallengeGameDTO.game().map(GameResponseMapper::toDTO).orElse(null)
        );
    }
}
