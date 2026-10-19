package com.github.sergiohrvas.queryle.challenges.application.commands.submitattempt;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.github.sergiohrvas.queryle.challenges.domain.models.DailyChallenge;
import com.github.sergiohrvas.queryle.challenges.domain.models.DataContext;
import com.github.sergiohrvas.queryle.challenges.domain.models.Game;
import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.Attempt;
import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.Query;
import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.QueryEvaluation;
import com.github.sergiohrvas.queryle.challenges.domain.repositories.DailyChallengeRepository;
import com.github.sergiohrvas.queryle.challenges.domain.repositories.DataContextRepository;
import com.github.sergiohrvas.queryle.challenges.domain.repositories.GameRepository;
import com.github.sergiohrvas.queryle.challenges.domain.services.QueryEvaluatorService;

import jakarta.transaction.Transactional;

@Service
public class SubmitAttemptCommandHandler {

        private final DailyChallengeRepository dailyChallengeRepository;
        private final DataContextRepository dataContextRepository;
        private final GameRepository gameRepository;
        private final QueryEvaluatorService queryEvaluator;

        public SubmitAttemptCommandHandler(
                DailyChallengeRepository dailyChallengeRepository,
                DataContextRepository dataContextRepository,
                GameRepository gameRepository,
                QueryEvaluatorService queryEvaluator
        ) {
                this.dailyChallengeRepository = dailyChallengeRepository;
                this.dataContextRepository = dataContextRepository;
                this.gameRepository = gameRepository;
                this.queryEvaluator = queryEvaluator;
        }

        @Transactional
        public void handle(SubmitAttemptCommand command) {
                String rawQuery = command.attempt();
                UUID playerId = UUID.fromString(command.playerId());
                UUID dailyChallengeId = UUID.fromString(command.dailyChallengeId());

                Query playerQuery = new Query(rawQuery);

                DailyChallenge dailyChallenge = dailyChallengeRepository.findById(dailyChallengeId)
                                .orElseThrow(() -> new IllegalArgumentException("Daily challenge not found"));

                DataContext dataContext = dataContextRepository.findById(dailyChallenge.getDataContextId())
                                .orElseThrow(() -> new IllegalArgumentException("Data context not found"));

                Game game = gameRepository.findByPlayerIdAndDailyChallengeId(playerId, dailyChallengeId)
                                .orElseGet(() -> new Game(playerId, dailyChallengeId, LocalDateTime.now()));

                QueryEvaluation evaluation = queryEvaluator.evaluate(playerQuery, dailyChallenge.getQuery(), dataContext);

                Attempt gameAttempt = new Attempt(playerQuery, evaluation, LocalDateTime.now());
                game.addAttempt(gameAttempt);

                gameRepository.save(game);
        }
}