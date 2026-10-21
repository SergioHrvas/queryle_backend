package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.out.persistence.dailychallenge.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.github.sergiohrvas.queryle.challenges.application.queries.getdailychallenge.DailyChallengeDetailView;
import com.github.sergiohrvas.queryle.challenges.application.queries.getdailychallenges.DailyChallengeSummaryView;

public interface DailyChallengeQueryRepository {
    public List<DailyChallengeSummaryView> getDailyChallenges(UUID playerId, LocalDate dateFrom, LocalDate dateTo, String difficulty);
    public DailyChallengeDetailView getDailyChallenge(UUID playerId, UUID challengeId);
}
