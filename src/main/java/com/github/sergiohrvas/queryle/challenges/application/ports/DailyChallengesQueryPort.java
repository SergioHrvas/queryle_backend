package com.github.sergiohrvas.queryle.challenges.application.ports;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.github.sergiohrvas.queryle.challenges.application.queries.getdailychallenge.views.DailyChallengeDetailView;
import com.github.sergiohrvas.queryle.challenges.application.queries.getdailychallenges.views.DailyChallengeSummaryView;

public interface DailyChallengesQueryPort {
    public List<DailyChallengeSummaryView> getDailyChallenges(
        UUID playerId, 
        LocalDate dateFrom, 
        LocalDate dateTo, 
        String difficulty
    );

    public List<DailyChallengeSummaryView> getDailyChallenges(
        UUID playerId, 
        LocalDate dateFrom,
        LocalDate dateTo
    );

    public DailyChallengeDetailView getDailyChallenge(
        UUID playerId, 
        UUID challengeId
    );
}
