package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.controllers;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.dtos.response.PlayerStatsResponseDTO;
import com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.mappers.PlayerStatsResponseMapper;
import com.github.sergiohrvas.queryle.challenges.application.usecases.GetPlayerStatsUseCase;
import com.github.sergiohrvas.queryle.challenges.domain.models.PlayerStats;

@RestController
@RequestMapping("/api/v1/player-stats")
public class PlayerStatsController {
    private final GetPlayerStatsUseCase getPlayerStatsUseCase;

    public PlayerStatsController(GetPlayerStatsUseCase getPlayerStatsUseCase) {
        this.getPlayerStatsUseCase = getPlayerStatsUseCase;
    }

    @GetMapping
    public ResponseEntity<PlayerStatsResponseDTO> getPlayerStats(@RequestHeader UUID playerId) {
        PlayerStats playerStats = getPlayerStatsUseCase.execute(playerId);
        return ResponseEntity.ok(PlayerStatsResponseMapper.toDTO(playerStats));
    }
}
