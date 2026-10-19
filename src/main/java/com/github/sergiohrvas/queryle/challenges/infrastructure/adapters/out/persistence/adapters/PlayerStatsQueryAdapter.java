package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.out.persistence.adapters;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.github.sergiohrvas.queryle.challenges.application.dtos.PlayerStatsReadModel;
import com.github.sergiohrvas.queryle.challenges.application.ports.PlayerStatsQueryPort;
import com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.out.persistence.repositories.H2JdbcPlayerStatsQueryRepository;

@Component
public class PlayerStatsQueryAdapter implements PlayerStatsQueryPort {

    private final H2JdbcPlayerStatsQueryRepository repository;

    public PlayerStatsQueryAdapter(H2JdbcPlayerStatsQueryRepository repository) {
        this.repository = repository;
    }

    @Override
    public PlayerStatsReadModel findByPlayerId(UUID playerId) {
        return repository.getStats(playerId);
    }
}