package com.github.sergiohrvas.queryle.challenges.application.commands.submitattempt;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.github.sergiohrvas.queryle.challenges.application.exceptions.DailyChallengeNotFoundException;
import com.github.sergiohrvas.queryle.challenges.application.exceptions.DataContextNotFoundException;
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
                UUID playerId = command.playerId();
                UUID dailyChallengeId = command.dailyChallengeId();
                LocalDateTime now = LocalDateTime.now();
                
                Query playerQuery = new Query(command.rawQuery());
                DailyChallenge dailyChallenge = dailyChallengeRepository.findById(dailyChallengeId)
                                .orElseThrow(() -> new DailyChallengeNotFoundException(dailyChallengeId));

                DataContext dataContext = dataContextRepository.findById(dailyChallenge.getDataContextId())
                                .orElseThrow(() -> new DataContextNotFoundException(dailyChallenge.getDataContextId()));

                Game game = gameRepository.findByPlayerIdAndDailyChallengeId(playerId, dailyChallengeId)
                                .orElseGet(() -> new Game(playerId, dailyChallengeId, now));

                QueryEvaluation evaluation = queryEvaluator.evaluate(playerQuery, dailyChallenge.getQuery(), dataContext);

                Attempt gameAttempt = new Attempt(playerQuery, evaluation, now);
                game.addAttempt(gameAttempt);

                gameRepository.save(game);
        }
}