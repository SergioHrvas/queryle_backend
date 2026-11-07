package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.out.persistence.playerstats;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.github.sergiohrvas.queryle.challenges.application.ports.PlayerStatsQueryPort;
import com.github.sergiohrvas.queryle.challenges.application.queries.getplayerstats.PlayerStatsView;
import com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.out.persistence.playerstats.repositories.PlayerStatsQueryRepository;

@Component
public class PlayerStatsAdapter implements PlayerStatsQueryPort {

    private final PlayerStatsQueryRepository repository;

    public PlayerStatsAdapter(PlayerStatsQueryRepository repository) {
        this.repository = repository;
    }

    @Override
    public PlayerStatsView findByPlayerId(UUID playerId) {
        return repository.getStats(playerId);
    }
}