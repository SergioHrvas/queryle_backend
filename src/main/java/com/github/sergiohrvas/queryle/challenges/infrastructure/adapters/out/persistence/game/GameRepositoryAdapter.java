package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.out.persistence.game;

import com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.out.persistence.game.mappers.GameEntityMapper;
import com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.out.persistence.game.repositories.GameJPARepository;
import com.github.sergiohrvas.queryle.challenges.domain.models.Game;
import com.github.sergiohrvas.queryle.challenges.domain.repositories.GameRepository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class GameRepositoryAdapter implements GameRepository {
    private final GameJPARepository gameJPARepository;

    public GameRepositoryAdapter(GameJPARepository gameJPARepository) {
        this.gameJPARepository = gameJPARepository;
    }

    @Override
    public Game save(Game game) {
        return GameEntityMapper.toDomain(gameJPARepository.save(GameEntityMapper.toEntity(game)));
    }

    @Override
    public Optional<Game> findById(UUID id) {
        return gameJPARepository.findById(id).map(GameEntityMapper::toDomain);
    }

    @Override
    public Optional<Game> findByPlayerIdAndDailyChallengeId(UUID playerId, UUID dailyChallengeId) {
        return gameJPARepository.findByPlayerIdAndDailyChallengeId(playerId, dailyChallengeId).map(GameEntityMapper::toDomain);
    }
}
