package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.out.persistence.adapters;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.github.sergiohrvas.queryle.challenges.domain.models.DailyChallenge;
import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.Difficulty;
import com.github.sergiohrvas.queryle.challenges.domain.ports.DailyChallengePort;
import com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.out.persistence.mappers.DailyChallengeEntityMapper;
import com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.out.persistence.repositories.DailyChallengeJPARepository;

@Component
public class DailyChallengeAdapter implements DailyChallengePort {
    private final DailyChallengeJPARepository dailyChallengeJPARepository;

    public DailyChallengeAdapter(DailyChallengeJPARepository dailyChallengeJPARepository) {
        this.dailyChallengeJPARepository = dailyChallengeJPARepository;
    }

    @Override
    public DailyChallenge save(DailyChallenge dailyChallenge) {
        return DailyChallengeEntityMapper.toDomain(dailyChallengeJPARepository.save(DailyChallengeEntityMapper.toEntity(dailyChallenge)));
    }

    @Override
    public Optional<DailyChallenge> findById(UUID id) {
        return dailyChallengeJPARepository.findById(id).map(DailyChallengeEntityMapper::toDomain);
    }

    @Override
    public Optional<DailyChallenge> findByPublicationDateAndDifficulty(LocalDate publicationDate, Difficulty difficulty) {
        return dailyChallengeJPARepository.findByPublicationDateAndDifficulty(publicationDate, difficulty.name()).map(DailyChallengeEntityMapper::toDomain);
    }
}
