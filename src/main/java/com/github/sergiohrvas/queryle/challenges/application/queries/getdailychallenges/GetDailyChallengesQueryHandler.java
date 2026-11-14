package com.github.sergiohrvas.queryle.challenges.application.queries.getdailychallenges;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.github.sergiohrvas.queryle.challenges.application.ports.CurrentPlayerPort;
import com.github.sergiohrvas.queryle.challenges.application.ports.DailyChallengesQueryPort;
import com.github.sergiohrvas.queryle.challenges.application.queries.getdailychallenges.views.DailyChallengeSummaryView;

@Service 
public class GetDailyChallengesQueryHandler {
    
    private final DailyChallengesQueryPort dailyChallengesQueryPort;
    private final CurrentPlayerPort currentPlayerPort;

    public GetDailyChallengesQueryHandler(DailyChallengesQueryPort dailyChallengesQueryPort, CurrentPlayerPort currentPlayerPort) {
        this.dailyChallengesQueryPort = dailyChallengesQueryPort;
        this.currentPlayerPort = currentPlayerPort;
    }

    public List<DailyChallengeSummaryView> handle(GetDailyChallengesQuery query) {
        UUID playerId = currentPlayerPort.getCurrentPlayerId(); 
        
        return dailyChallengesQueryPort.getDailyChallenges(
            playerId,
            query.dateFrom(),
            query.dateTo(),
            query.difficulty()
        );
    }
}