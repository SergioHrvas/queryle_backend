package com.github.sergiohrvas.queryle.challenges.application.commands.createdailychallenge;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.github.sergiohrvas.queryle.challenges.domain.models.DailyChallenge;
import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.Difficulty;
import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.Query;
import com.github.sergiohrvas.queryle.challenges.domain.repositories.DailyChallengeRepository;

import jakarta.transaction.Transactional;

@Service 
public class CreateDailyChallengeCommandHandler {
    
    private final DailyChallengeRepository dailyChallengeRepository;
    
    public CreateDailyChallengeCommandHandler(
        DailyChallengeRepository dailyChallengeRepository
    ) {
        this.dailyChallengeRepository = dailyChallengeRepository;
    }

    @Transactional
    public void handle(CreateDailyChallengeCommand command) {
       UUID id = dailyChallengeRepository.generateId();

       Query query = new Query(command.query());
       Difficulty difficulty = Difficulty.valueOf(command.difficulty().toUpperCase());
       
       DailyChallenge dailyChallenge = new DailyChallenge(
            id,
            command.formulation(),
            query,
            difficulty,
            command.publicationDate(),
            command.dataContextId()
        );

        dailyChallengeRepository.save(dailyChallenge);
    }
}
