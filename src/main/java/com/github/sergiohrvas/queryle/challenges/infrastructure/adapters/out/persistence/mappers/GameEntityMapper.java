package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.out.persistence.mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.out.persistence.entities.AttemptEntity;
import com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.out.persistence.entities.GameEntity;
import com.github.sergiohrvas.queryle.challenges.domain.models.Game;
import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.Attempt;
import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.Feedback;
import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.GameStatus;
import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.MatchLevel;
import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.Query;

public class GameEntityMapper {
    public static GameEntity toEntity(Game game) {
        if(Objects.isNull(game)) {
            throw new IllegalArgumentException("Game cannot be null");
        }

        GameEntity newGame = new GameEntity(
            game.getId(),
            game.getPlayerId(),
            game.getDailyChallengeId(),
            game.getStartedDate(),
            game.getStatus().name(),
            new ArrayList<>()
        );

        game.getAttempts().stream()
        .map(attempt -> mapToAttemptEntity(attempt, newGame)).forEach(newGame::addAttempt);

        return newGame;
    }

    public static Game toDomain(GameEntity gameEntity) {
        if(Objects.isNull(gameEntity)) {
            throw new IllegalArgumentException("Game entity cannot be null");
        }

        List<Attempt> attempts = gameEntity.getAttempts().stream()
            .map(GameEntityMapper::mapToAttempt)
            .toList();

        return new Game(
            gameEntity.getId(),
            gameEntity.getPlayerId(),
            gameEntity.getDailyChallengeId(),
            gameEntity.getStartedDate(),
            GameStatus.valueOf(gameEntity.getStatus()),
            attempts
        );
    }

    private static Attempt mapToAttempt(AttemptEntity attemptEntity) {
        return new Attempt(
            new Query(attemptEntity.getQuery()),
            new Feedback(
                MatchLevel.valueOf(attemptEntity.getColumnsNamesMatchLevel()),
                MatchLevel.valueOf(attemptEntity.getColumnsCountMatchLevel()),
                MatchLevel.valueOf(attemptEntity.getRowsCountMatchLevel()),
                MatchLevel.valueOf(attemptEntity.getRowsDataMatchLevel()),
                MatchLevel.valueOf(attemptEntity.getRowsOrderMatchLevel())
            ),
            attemptEntity.getDate()
        );
    }

    private static AttemptEntity mapToAttemptEntity(Attempt attempt, GameEntity gameEntity) {
        return new AttemptEntity(
            gameEntity,
            attempt.query().value(),
            attempt.date(),
            attempt.feedback().columnsNamesMatchLevel().name(),
            attempt.feedback().columnsCountMatchLevel().name(),
            attempt.feedback().rowsCountMatchLevel().name(),
            attempt.feedback().rowsDataMatchLevel().name(),
            attempt.feedback().rowsOrderMatchLevel().name()
        );
    }
}
