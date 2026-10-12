package com.github.sergiohrvas.queryle.challenges.application.commands;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.github.sergiohrvas.queryle.challenges.domain.models.DailyChallenge;
import com.github.sergiohrvas.queryle.challenges.domain.models.DataContext;
import com.github.sergiohrvas.queryle.challenges.domain.models.Game;
import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.Attempt;
import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.Feedback;
import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.Query;
import com.github.sergiohrvas.queryle.challenges.domain.ports.DailyChallengePort;
import com.github.sergiohrvas.queryle.challenges.domain.ports.DataContextPort;
import com.github.sergiohrvas.queryle.challenges.domain.ports.GamePort;
import com.github.sergiohrvas.queryle.challenges.domain.services.QueryEvaluatorService;

import jakarta.transaction.Transactional;

@Service
public class SubmitAttemptCommandHandler {

        private final DailyChallengePort dailyChallengePort;
        private final DataContextPort dataContextPort;
        private final GamePort gamePort;
        private final QueryEvaluatorService queryEvaluator;
        private final ApplicationEventPublisher eventPublisher;

        public SubmitAttemptCommandHandler(
                DailyChallengePort dailyChallengePort,
                DataContextPort dataContextPort,
                GamePort gamePort,
                QueryEvaluatorService queryEvaluator,
                ApplicationEventPublisher eventPublisher
        ) {
                this.dailyChallengePort = dailyChallengePort;
                this.dataContextPort = dataContextPort;
                this.gamePort = gamePort;
                this.queryEvaluator = queryEvaluator;
                this.eventPublisher = eventPublisher;
        }

        @Transactional
        public void handle(SubmitAttemptCommand command) {
                String rawQuery = command.attempt();
                UUID playerId = UUID.fromString(command.playerId());
                UUID dailyChallengeId = UUID.fromString(command.dailyChallengeId());

                Query playerQuery = new Query(rawQuery);

                DailyChallenge dailyChallenge = dailyChallengePort.findById(dailyChallengeId)
                                .orElseThrow(() -> new IllegalArgumentException("Daily challenge not found"));

                DataContext dataContext = dataContextPort.findById(dailyChallenge.getDataContextId())
                                .orElseThrow(() -> new IllegalArgumentException("Data context not found"));

                Game game = gamePort.findByPlayerIdAndDailyChallengeId(playerId, dailyChallengeId)
                                .orElseGet(() -> new Game(playerId, dailyChallengeId, LocalDateTime.now()));

                Feedback feedback = queryEvaluator.evaluate(playerQuery, dailyChallenge.getQuery(), dataContext);

                Attempt gameAttempt = new Attempt(playerQuery, feedback, LocalDateTime.now());
                game.addAttempt(gameAttempt);

                gamePort.save(game);
                game.pullDomainEvents().forEach(eventPublisher::publishEvent);
        }
}