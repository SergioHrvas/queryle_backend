package com.github.sergiohrvas.queryle.challenges.application.usecases;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;


import com.github.sergiohrvas.queryle.challenges.domain.models.DailyChallenge;
import com.github.sergiohrvas.queryle.challenges.domain.models.DataContext;
import com.github.sergiohrvas.queryle.challenges.domain.models.Game;
import com.github.sergiohrvas.queryle.challenges.domain.models.PlayerStats;
import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.Attempt;
import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.Feedback;
import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.GameStatus;
import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.Query;

import com.github.sergiohrvas.queryle.challenges.domain.ports.PlayerStatsPort;
import com.github.sergiohrvas.queryle.challenges.domain.ports.GamePort;
import com.github.sergiohrvas.queryle.challenges.domain.ports.DailyChallengePort;
import com.github.sergiohrvas.queryle.challenges.domain.ports.DataContextPort;
import com.github.sergiohrvas.queryle.challenges.domain.ports.QueryEvaluatorPort;

import jakarta.transaction.Transactional;

@Service
public class SubmitAttemptUseCase {
    
    private final PlayerStatsPort playerStatsPort;
    private final DailyChallengePort dailyChallengePort;
    private final DataContextPort dataContextPort;
    private final GamePort gamePort;
    private final QueryEvaluatorPort queryEvaluator;

    public SubmitAttemptUseCase(PlayerStatsPort playerStatsPort, DailyChallengePort dailyChallengePort, DataContextPort dataContextPort, GamePort gamePort, QueryEvaluatorPort queryEvaluator) {
        this.playerStatsPort = playerStatsPort;
        this.dailyChallengePort = dailyChallengePort;
        this.dataContextPort = dataContextPort;
        this.gamePort = gamePort;
        this.queryEvaluator = queryEvaluator;
    }

    @Transactional
    public void execute(UUID userId, UUID dailyChallengeId, String rawQuery) {
        
        Query userQuery = new Query(rawQuery);

        // 1. Get the daily challenge
        DailyChallenge dailyChallenge = dailyChallengePort.findById(dailyChallengeId)
            .orElseThrow(() -> new IllegalArgumentException("Daily challenge not found"));

        // 2. Get the data context
        DataContext dataContext = dataContextPort.findById(dailyChallenge.getDataContextId())
            .orElseThrow(() -> new IllegalArgumentException("Data context not found"));
        
        // 3. Get the game
        Game game = gamePort.findByPlayerIdAndDailyChallengeId(userId, dailyChallengeId)
            .orElseGet(() -> new Game(userId, dailyChallengeId, LocalDateTime.now()));

        Feedback feedback = queryEvaluator.evaluate(userQuery, dailyChallenge.getQuery(), dataContext);

        // 4. Create the game attempt
        Attempt gameAttempt = new Attempt(userQuery, feedback, LocalDateTime.now());
        game.addAttempt(gameAttempt);

        // 5. Update the player stats
        if(GameStatus.COMPLETED.equals(game.getStatus())) {
            PlayerStats playerStats = playerStatsPort.findByUserId(userId)
                .orElseGet(() -> new PlayerStats(userId));
            
            int newLastChallengeSequence = dailyChallenge.getSequence();
            boolean isChallengeOfToday = dailyChallenge.getPublicationDate().equals(LocalDate.now());

            playerStats.recordGameResult(game.isWon(), newLastChallengeSequence, isChallengeOfToday);
            playerStatsPort.save(playerStats);
        }
        
        // 6. Save the game
        gamePort.save(game);
    }

}
