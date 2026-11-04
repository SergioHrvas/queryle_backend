package com.github.sergiohrvas.queryle.challenges.application.queries.getdailychallenges;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.sergiohrvas.queryle.challenges.application.ports.DailyChallengesQueryPort;

@Service 
public class GetDailyChallengesQueryHandler {
    
    private final DailyChallengesQueryPort dailyChallengesQueryPort;

    public GetDailyChallengesQueryHandler(DailyChallengesQueryPort dailyChallengesQueryPort) {
        this.dailyChallengesQueryPort = dailyChallengesQueryPort;
    }

    public List<DailyChallengeSummaryView> handle(GetDailyChallengesQuery query) {
        return dailyChallengesQueryPort.getDailyChallenges(
            query.playerId(),
            query.dateFrom(),
            query.dateTo(),
            query.difficulty()
        );
    }
}