package com.github.sergiohrvas.queryle.challenges.infrastructure.controllers;

import java.util.UUID;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.github.sergiohrvas.queryle.challenges.application.usecases.SubmitAttemptUseCase;
import com.github.sergiohrvas.queryle.challenges.application.dtos.DailyChallengeResponseDTO;
import com.github.sergiohrvas.queryle.challenges.application.mappers.DailyChallengeMapper;
import com.github.sergiohrvas.queryle.challenges.application.usecases.GetDailyChallengeUseCase;
import com.github.sergiohrvas.queryle.challenges.domain.models.DailyChallenge;

@RestController
@RequestMapping("/api/v1/challenges")
public class DailyChallengeController {
    private final SubmitAttemptUseCase submitAttemptUseCase;
    private final GetDailyChallengeUseCase getDailyChallengeUseCase;

    public DailyChallengeController(SubmitAttemptUseCase submitAttemptUseCase, GetDailyChallengeUseCase getDailyChallengeUseCase) {
        this.submitAttemptUseCase = submitAttemptUseCase;
        this.getDailyChallengeUseCase = getDailyChallengeUseCase;
    }

    @PostMapping("/{dailyChallengeId}/submit-attempt")
    public ResponseEntity<Void> submitAttempt(@PathVariable UUID dailyChallengeId, @RequestHeader UUID playerId, @RequestBody String query) {
        submitAttemptUseCase.execute(dailyChallengeId, playerId, query);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{dailyChallengeId}/daily-challenge")
    public ResponseEntity<DailyChallengeResponseDTO> getDailyChallenge(@PathVariable UUID dailyChallengeId) {
        DailyChallenge dailyChallenge = getDailyChallengeUseCase.execute(dailyChallengeId);
        return ResponseEntity.ok(DailyChallengeMapper.toDTO(dailyChallenge));
    }
}
