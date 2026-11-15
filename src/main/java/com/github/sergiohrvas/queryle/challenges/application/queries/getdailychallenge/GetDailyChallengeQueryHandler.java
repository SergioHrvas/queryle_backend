package com.github.sergiohrvas.queryle.challenges.application.queries.getdailychallenge;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.github.sergiohrvas.queryle.challenges.application.ports.CurrentPlayerPort;
import com.github.sergiohrvas.queryle.challenges.application.ports.DailyChallengesQueryPort;
import com.github.sergiohrvas.queryle.challenges.application.queries.getdailychallenge.views.DailyChallengeDetailView;

@Service
public class GetDailyChallengeQueryHandler {
    private final DailyChallengesQueryPort dailyChallengesQueryPort;
    private final CurrentPlayerPort currentPlayerPort;    

    public GetDailyChallengeQueryHandler(DailyChallengesQueryPort dailyChallengesQueryPort, CurrentPlayerPort currentPlayerPort) {
        this.dailyChallengesQueryPort = dailyChallengesQueryPort;
        this.currentPlayerPort = currentPlayerPort;
    }

    public DailyChallengeDetailView handle(GetDailyChallengeQuery query) {
        UUID playerId = currentPlayerPort.getCurrentPlayerId();
        
        return dailyChallengesQueryPort.getDailyChallenge(
            playerId,
            query.challengeId()
        );
    }
}
