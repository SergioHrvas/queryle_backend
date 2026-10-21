package com.github.sergiohrvas.queryle.challenges.application.queries.getdailychallenge;

import org.springframework.stereotype.Service;

import com.github.sergiohrvas.queryle.challenges.application.ports.DailyChallengesQueryPort;

@Service
public class GetDailyChallengeQueryHandler {
        private final DailyChallengesQueryPort dailyChallengesQueryPort;

    public GetDailyChallengeQueryHandler(DailyChallengesQueryPort dailyChallengesQueryPort) {
        this.dailyChallengesQueryPort = dailyChallengesQueryPort;
    }

    public DailyChallengeDetailView handle(GetDailyChallengeQuery query) {
        return dailyChallengesQueryPort.getDailyChallenge(
            query.playerId(),
            query.challengeId()
        );
    }
}
