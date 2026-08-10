package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.out.persistence.mappers;

import java.util.Objects;

import com.github.sergiohrvas.queryle.challenges.domain.models.DailyChallenge;
import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.Difficulty;
import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.Query;
import com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.out.persistence.entities.DailyChallengeEntity;

public class DailyChallengeEntityMapper {
    
    public static DailyChallengeEntity toEntity(DailyChallenge dailyChallenge) {
        if(Objects.isNull(dailyChallenge)) {
            throw new IllegalArgumentException("Daily challenge cannot be null");
        }

        return new DailyChallengeEntity(
            dailyChallenge.getId(),
            dailyChallenge.getFormulation(),
            dailyChallenge.getQuery().value(),
            dailyChallenge.getDifficulty().name(),
            dailyChallenge.getPublicationDate(),
            dailyChallenge.getDataContextId(),
            dailyChallenge.getSequence()
        );
    }

    public static DailyChallenge toDomain(DailyChallengeEntity dailyChallengeEntity) {
        if(Objects.isNull(dailyChallengeEntity)) {
            throw new IllegalArgumentException("Daily challenge entity cannot be null");
        }

        return new DailyChallenge(
            dailyChallengeEntity.getId(),
            dailyChallengeEntity.getFormulation(),
            new Query(dailyChallengeEntity.getQuery()),
            dailyChallengeEntity.getSequence(),
            Difficulty.valueOf(dailyChallengeEntity.getDifficulty()),
            dailyChallengeEntity.getPublicationDate(),
            dailyChallengeEntity.getDataContextId()
        );
    }
}
