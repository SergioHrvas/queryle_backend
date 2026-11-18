package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.GetMapping;

import com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.dtos.response.PlayerStatsResponseDTO;
import com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.mappers.PlayerStatsResponseMapper;
import com.github.sergiohrvas.queryle.challenges.application.queries.getplayerstats.GetPlayerStatsQueryHandler;
import com.github.sergiohrvas.queryle.challenges.application.queries.getplayerstats.views.PlayerStatsView;

@RestController
@RequestMapping("/api/v1/player-stats")
public class PlayerStatsController {
    private final GetPlayerStatsQueryHandler getPlayerStatsQueryHandler;
    
    public PlayerStatsController(GetPlayerStatsQueryHandler getPlayerStatsQueryHandler) {
        this.getPlayerStatsQueryHandler = getPlayerStatsQueryHandler;    
    }

    @GetMapping("/me")
    public ResponseEntity<PlayerStatsResponseDTO> getPlayerStats() {
        PlayerStatsView playerStats = getPlayerStatsQueryHandler.handle();
        return ResponseEntity.ok(PlayerStatsResponseMapper.toDTO(playerStats));
    }
}
