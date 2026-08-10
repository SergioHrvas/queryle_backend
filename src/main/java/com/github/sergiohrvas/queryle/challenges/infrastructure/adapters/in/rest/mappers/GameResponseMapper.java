package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.mappers;

import java.util.Objects;

import com.github.sergiohrvas.queryle.challenges.domain.models.Game;
import com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.dtos.response.GameResponseDTO;

public class GameResponseMapper {
    public static GameResponseDTO toDTO(Game game) {
        if (Objects.isNull(game)) return null;

        return new GameResponseDTO(
            game.getId(),
            game.getStartedDate(),
            game.getStatus(),
            game.getAttempts().stream().map(AttemptResponseMapper::toDTO).toList()
        );
    }
}
