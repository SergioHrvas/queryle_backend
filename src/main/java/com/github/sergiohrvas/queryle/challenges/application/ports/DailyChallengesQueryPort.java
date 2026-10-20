package com.github.sergiohrvas.queryle.challenges.application.ports;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.github.sergiohrvas.queryle.challenges.application.queries.getdailychallenges.DailyChallengeView;

public interface DailyChallengesQueryPort {
    public List<DailyChallengeView> getDailyChallenges(
        UUID playerId, 
        LocalDate dateFrom, 
        LocalDate dateTo, 
        String difficulty
    );
}
