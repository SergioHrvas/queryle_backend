package com.github.sergiohrvas.queryle.challenges.application.queries.getdailychallenge;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.github.sergiohrvas.queryle.challenges.application.exceptions.DailyChallengeNotAvailableException;
import com.github.sergiohrvas.queryle.challenges.application.exceptions.DailyChallengeNotFoundException;
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
        
        LocalDate today = LocalDate.now();
        DailyChallengeDetailView dailyChallenge = dailyChallengesQueryPort.getDailyChallenge(
            playerId,
            query.challengeId()
        );

        if (Objects.isNull(dailyChallenge)) {
            throw new DailyChallengeNotFoundException(query.challengeId());
        }

        if (dailyChallenge.publicationDate().isAfter(today)) {
            throw new DailyChallengeNotAvailableException(query.challengeId());
        }
        return dailyChallenge;
    }
}
