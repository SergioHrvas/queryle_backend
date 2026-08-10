package com.github.sergiohrvas.queryle.challenges.application.usecases;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.github.sergiohrvas.queryle.challenges.application.dtos.DailyChallengeGameDTO;
import com.github.sergiohrvas.queryle.challenges.application.exceptions.DailyChallengeNotFoundException;
import com.github.sergiohrvas.queryle.challenges.domain.models.DailyChallenge;
import com.github.sergiohrvas.queryle.challenges.domain.models.Game;
import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.Difficulty;
import com.github.sergiohrvas.queryle.challenges.domain.ports.DailyChallengePort;
import com.github.sergiohrvas.queryle.challenges.domain.ports.GamePort;

@Service
public class GetDailyChallengeUseCase {
    private final DailyChallengePort dailyChallengePort;
    private final GamePort gamePort;

    public GetDailyChallengeUseCase(DailyChallengePort dailyChallengePort, GamePort gamePort) {
        this.dailyChallengePort = dailyChallengePort;
        this.gamePort = gamePort;
    }

    public DailyChallengeGameDTO execute(UUID dailyChallengeId, UUID playerId) {
        DailyChallenge dailyChallenge = dailyChallengePort.findById(dailyChallengeId).orElseThrow(() -> new DailyChallengeNotFoundException(dailyChallengeId));
        Optional<Game> game = gamePort.findByPlayerIdAndDailyChallengeId(playerId, dailyChallenge.getId());
        return new DailyChallengeGameDTO(dailyChallenge, game);
    }

    public DailyChallengeGameDTO execute(LocalDate publicationDate, Difficulty difficulty, UUID playerId) {
        DailyChallenge dailyChallenge = dailyChallengePort.findByPublicationDateAndDifficulty(publicationDate, difficulty).orElseThrow(() -> new DailyChallengeNotFoundException(publicationDate, difficulty));
        Optional<Game> game = gamePort.findByPlayerIdAndDailyChallengeId(playerId, dailyChallenge.getId());
        return new DailyChallengeGameDTO(dailyChallenge, game);
    }
}
