package com.github.sergiohrvas.queryle.challenges.application.usecases;

import java.time.LocalDate;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.github.sergiohrvas.queryle.challenges.domain.events.GameCompletedEvent;
import com.github.sergiohrvas.queryle.challenges.domain.models.DailyChallenge;
import com.github.sergiohrvas.queryle.challenges.domain.models.PlayerStats;
import com.github.sergiohrvas.queryle.challenges.domain.ports.DailyChallengePort;
import com.github.sergiohrvas.queryle.challenges.domain.ports.PlayerStatsPort;

import jakarta.transaction.Transactional;

@Service
public class UpdatePlayerStatsOnGameCompleted {
    private final PlayerStatsPort playerStatsPort;
    private final DailyChallengePort dailyChallengePort;

    public UpdatePlayerStatsOnGameCompleted(
            PlayerStatsPort playerStatsPort,
            DailyChallengePort dailyChallengePort
    ) {
        this.playerStatsPort = playerStatsPort;
        this.dailyChallengePort = dailyChallengePort;
    }

    @EventListener
    @Transactional
    public void handle(GameCompletedEvent event) {
        PlayerStats stats = playerStatsPort
                .findByPlayerId(event.playerId())
                .orElseGet(() -> new PlayerStats(event.playerId()));

        DailyChallenge challenge = dailyChallengePort
                .findById(event.dailyChallengeId())
                .orElseThrow(() -> new IllegalStateException("Daily challenge not found"));

        stats.recordGameResult(
                event.won(),
                challenge.getSequence(),
                challenge.getPublicationDate().equals(LocalDate.now()));

        playerStatsPort.save(stats);
    }
}