package com.github.sergiohrvas.queryle.challenges.domain.ports;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import com.github.sergiohrvas.queryle.challenges.domain.models.DailyChallenge;
import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.Difficulty;

public interface DailyChallengePort {
    DailyChallenge save(DailyChallenge dailyChallenge);
    Optional<DailyChallenge> findById(UUID id);
    Optional<DailyChallenge> findByPublicationDateAndDifficulty(LocalDate publicationDate, Difficulty difficulty);
}
