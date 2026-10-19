package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.controllers;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.dtos.response.PlayerStatsResponseDTO;
import com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.mappers.PlayerStatsResponseMapper;
import com.github.sergiohrvas.queryle.challenges.application.queries.getplayerstats.GetPlayerStatsQuery;
import com.github.sergiohrvas.queryle.challenges.application.queries.getplayerstats.GetPlayerStatsQueryHandler;
import com.github.sergiohrvas.queryle.challenges.application.queries.getplayerstats.PlayerStatsReadModel;

@RestController
@RequestMapping("/api/v1/player-stats")
public class PlayerStatsController {
    private final GetPlayerStatsQueryHandler getPlayerStatsQueryHandler;
    
    public PlayerStatsController(GetPlayerStatsQueryHandler getPlayerStatsQueryHandler) {
        this.getPlayerStatsQueryHandler = getPlayerStatsQueryHandler;    
    }

    @GetMapping("/{playerId}")
    public ResponseEntity<PlayerStatsResponseDTO> getPlayerStats(@PathVariable UUID playerId) {
        GetPlayerStatsQuery query = new GetPlayerStatsQuery(playerId);
        PlayerStatsReadModel playerStats = getPlayerStatsQueryHandler.handle(query);
        return ResponseEntity.ok(PlayerStatsResponseMapper.toDTO(playerStats));
    }
}
