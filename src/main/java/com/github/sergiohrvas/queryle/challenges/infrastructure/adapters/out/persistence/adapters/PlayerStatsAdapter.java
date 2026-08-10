package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.out.persistence.adapters;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.github.sergiohrvas.queryle.challenges.domain.models.PlayerStats;
import com.github.sergiohrvas.queryle.challenges.domain.ports.PlayerStatsPort;
import com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.out.persistence.mappers.PlayerStatsEntityMapper;
import com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.out.persistence.repositories.PlayerStatsJpaRepository;

@Component
public class PlayerStatsAdapter implements PlayerStatsPort {
    private final PlayerStatsJpaRepository playerStatsRepository;

    public PlayerStatsAdapter(PlayerStatsJpaRepository playerStatsRepository) {
        this.playerStatsRepository = playerStatsRepository;
    }

    @Override
    public Optional<PlayerStats> findByPlayerId(UUID playerId) {
        return playerStatsRepository.findById(playerId).map(PlayerStatsEntityMapper::toDomain);
    }

    @Override
    public PlayerStats save(PlayerStats playerStats) {
        return PlayerStatsEntityMapper.toDomain(playerStatsRepository.save(PlayerStatsEntityMapper.toEntity(playerStats)));
    }
}
