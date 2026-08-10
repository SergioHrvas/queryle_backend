package com.github.sergiohrvas.queryle.challenges.application.usecases;

import java.util.List;
import java.util.UUID;

import com.github.sergiohrvas.queryle.challenges.application.dtos.DailyChallengeGameDTO;
import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.Difficulty;

import org.springframework.stereotype.Service;

@Service
public class GetDailyChallengesUseCase {


    public List<DailyChallengeGameDTO> execute(int page, int size, Difficulty difficulty, UUID playerId) {
        //TODO
        return List.of();
    }
}
