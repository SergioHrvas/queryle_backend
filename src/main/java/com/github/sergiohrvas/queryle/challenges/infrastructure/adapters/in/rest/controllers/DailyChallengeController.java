package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.controllers;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.dtos.request.SubmitAttemptRequestDTO;
import com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.dtos.response.DailyChallengeResponseDTO;
import com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.mappers.DailyChallengeMapper;
import com.github.sergiohrvas.queryle.challenges.application.usecases.GetDailyChallengeUseCase;
import com.github.sergiohrvas.queryle.challenges.application.usecases.SubmitAttemptUseCase;
import com.github.sergiohrvas.queryle.challenges.domain.models.DailyChallenge;
import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.Difficulty;

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
    public ResponseEntity<Void> submitAttempt(@PathVariable UUID dailyChallengeId, @RequestHeader UUID playerId, @RequestBody SubmitAttemptRequestDTO request) {
        submitAttemptUseCase.execute(dailyChallengeId, playerId, request.query());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/today")
    public ResponseEntity<DailyChallengeResponseDTO> getTodayDailyChallenge(@RequestParam Difficulty difficulty) {
        LocalDate publicationDate = LocalDate.now();
        DailyChallenge dailyChallenge = getDailyChallengeUseCase.execute(publicationDate, difficulty);
        return ResponseEntity.ok(DailyChallengeMapper.toDTO(dailyChallenge));
    }

    @GetMapping("/{dailyChallengeId}")
    public ResponseEntity<DailyChallengeResponseDTO> getDailyChallenge(@PathVariable UUID dailyChallengeId) {
        DailyChallenge dailyChallenge = getDailyChallengeUseCase.execute(dailyChallengeId);
        return ResponseEntity.ok(DailyChallengeMapper.toDTO(dailyChallenge));
    }
}
