package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.out.persistence.playerstats;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.github.sergiohrvas.queryle.challenges.application.ports.PlayerStatsQueryPort;
import com.github.sergiohrvas.queryle.challenges.application.queries.getplayerstats.PlayerStatsReadModel;
import com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.out.persistence.playerstats.repositories.PlayerStatsRepository;

@Component
public class PlayerStatsQueryAdapter implements PlayerStatsQueryPort {

    private final PlayerStatsRepository repository;

    public PlayerStatsQueryAdapter(PlayerStatsRepository repository) {
        this.repository = repository;
    }

    @Override
    public PlayerStatsReadModel findByPlayerId(UUID playerId) {
        return repository.getStats(playerId);
    }
}