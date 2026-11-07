package com.github.sergiohrvas.queryle.challenges.application.queries.getdailychallenges;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.github.sergiohrvas.queryle.challenges.application.ports.DailyChallengesQueryPort;
import com.github.sergiohrvas.queryle.challenges.application.queries.getdailychallenges.views.DailyChallengeSummaryView;

@Service 
public class GetTodayDailyChallengesQueryHandler {
    
    private final DailyChallengesQueryPort dailyChallengesQueryPort;

    public GetTodayDailyChallengesQueryHandler(DailyChallengesQueryPort dailyChallengesQueryPort) {
        this.dailyChallengesQueryPort = dailyChallengesQueryPort;
    }

    public List<DailyChallengeSummaryView> handle(GetTodayDailyChallengesQuery query) {
        LocalDate today = LocalDate.now();
        return dailyChallengesQueryPort.getDailyChallenges(
            query.playerId(),
            today,
            today
        );
    }
}