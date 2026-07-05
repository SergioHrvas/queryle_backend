package com.github.sergiohrvas.queryle.challenges.domain.ports;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import com.github.sergiohrvas.queryle.challenges.domain.models.DailyChallenge;

public interface DailyChallengePort {
    void save(DailyChallenge dailyChallenge);
    Optional<DailyChallenge> findById(UUID id);
    Optional<DailyChallenge> findByPublicationDate(LocalDate publicationDate);
}
