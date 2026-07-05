package com.github.sergiohrvas.queryle.challenges.application.usecases;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.github.sergiohrvas.queryle.challenges.domain.models.DailyChallenge;
import com.github.sergiohrvas.queryle.challenges.domain.ports.DailyChallengePort;

@Service
public class GetDailyChallengeUseCase {
    private final DailyChallengePort dailyChallengePort;

    public GetDailyChallengeUseCase(DailyChallengePort dailyChallengePort) {
        this.dailyChallengePort = dailyChallengePort;
    }

    public DailyChallenge getDailyChallenge(UUID dailyChallengeId) {
        return dailyChallengePort.findById(dailyChallengeId).orElseThrow(() -> new IllegalArgumentException("Daily challenge not found"));
    }
}
