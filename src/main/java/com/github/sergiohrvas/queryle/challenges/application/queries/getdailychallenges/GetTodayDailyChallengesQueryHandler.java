package com.github.sergiohrvas.queryle.challenges.application.queries.getdailychallenges;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.github.sergiohrvas.queryle.challenges.application.ports.CurrentPlayerPort;
import com.github.sergiohrvas.queryle.challenges.application.ports.DailyChallengesQueryPort;
import com.github.sergiohrvas.queryle.challenges.application.queries.getdailychallenges.views.DailyChallengeSummaryView;

@Service 
public class GetTodayDailyChallengesQueryHandler {
    
    private final DailyChallengesQueryPort dailyChallengesQueryPort;
    private final CurrentPlayerPort currentPlayerPort;

    public GetTodayDailyChallengesQueryHandler(DailyChallengesQueryPort dailyChallengesQueryPort, CurrentPlayerPort currentPlayerPort) {
        this.dailyChallengesQueryPort = dailyChallengesQueryPort;
        this.currentPlayerPort = currentPlayerPort;
    }

    public List<DailyChallengeSummaryView> handle() {
        UUID playerId = currentPlayerPort.getCurrentPlayerId();
        LocalDate today = LocalDate.now();
        return dailyChallengesQueryPort.getDailyChallenges(
            playerId,
            today,
            today
        );
    }
}