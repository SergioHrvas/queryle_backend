package com.github.sergiohrvas.queryle.challenges.application.usecases;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.github.sergiohrvas.queryle.challenges.domain.models.DailyChallenge;
import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.Difficulty;
import com.github.sergiohrvas.queryle.challenges.domain.ports.DailyChallengePort;

@Service
public class GetDailyChallengeUseCase {
    private final DailyChallengePort dailyChallengePort;

    public GetDailyChallengeUseCase(DailyChallengePort dailyChallengePort) {
        this.dailyChallengePort = dailyChallengePort;
    }

    public DailyChallenge execute(UUID dailyChallengeId) {
        return dailyChallengePort.findById(dailyChallengeId).orElseThrow(() -> new IllegalArgumentException("Daily challenge not found for id " + dailyChallengeId));
    }

    public DailyChallenge execute(LocalDate publicationDate, Difficulty difficulty) {
        return dailyChallengePort.findByPublicationDateAndDifficulty(publicationDate, difficulty).orElseThrow(() -> new IllegalArgumentException("Daily challenge not found for publication date " + publicationDate + " and difficulty " + difficulty));
    }
}
