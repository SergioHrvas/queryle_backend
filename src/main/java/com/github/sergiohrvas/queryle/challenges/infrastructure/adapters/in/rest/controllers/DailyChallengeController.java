package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.controllers;

import java.time.LocalDate;
import java.util.List;
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
import com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.dtos.response.DailyChallengeGameResponseDTO;
import com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.dtos.response.GameResponseDTO;
import com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.mappers.DailyChallengeGameResponseMapper;
import com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.mappers.GameResponseMapper;
import com.github.sergiohrvas.queryle.challenges.application.dtos.DailyChallengeGameDTO;
import com.github.sergiohrvas.queryle.challenges.application.usecases.GetDailyChallengeUseCase;
import com.github.sergiohrvas.queryle.challenges.application.usecases.GetDailyChallengesUseCase;
import com.github.sergiohrvas.queryle.challenges.application.usecases.SubmitAttemptUseCase;
import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.Difficulty;

@RestController
@RequestMapping("/api/v1/challenges")
public class DailyChallengeController {
    private final SubmitAttemptUseCase submitAttemptUseCase;
    private final GetDailyChallengeUseCase getDailyChallengeUseCase;
    private final GetDailyChallengesUseCase getDailyChallengesUseCase;

    public DailyChallengeController(SubmitAttemptUseCase submitAttemptUseCase, GetDailyChallengeUseCase getDailyChallengeUseCase, GetDailyChallengesUseCase getDailyChallengesUseCase) {
        this.submitAttemptUseCase = submitAttemptUseCase;
        this.getDailyChallengeUseCase = getDailyChallengeUseCase;
        this.getDailyChallengesUseCase = getDailyChallengesUseCase;
    }


    @GetMapping
    public ResponseEntity<List<DailyChallengeGameResponseDTO>> getDailyChallenges(@PathVariable int page, @PathVariable int size, @RequestParam Difficulty difficulty, @RequestHeader UUID playerId) {
        List<DailyChallengeGameDTO> dailyChallenges = getDailyChallengesUseCase.execute(page, size, difficulty, playerId);
        return ResponseEntity.ok(dailyChallenges.stream().map(DailyChallengeGameResponseMapper::toDTO).toList());
    }


    @PostMapping("/{dailyChallengeId}/submit-attempt")
    public ResponseEntity<GameResponseDTO> submitAttempt(@PathVariable UUID dailyChallengeId, @RequestHeader UUID playerId, @RequestBody SubmitAttemptRequestDTO request) {
        return ResponseEntity.ok(GameResponseMapper.toDTO(submitAttemptUseCase.execute(playerId, dailyChallengeId, request.query())));
    }

    @GetMapping("/today")
    public ResponseEntity<DailyChallengeGameResponseDTO> getTodayDailyChallenge(@RequestParam Difficulty difficulty, @RequestHeader UUID playerId) {
        LocalDate publicationDate = LocalDate.now();
        DailyChallengeGameDTO dailyChallengeGameDTO = getDailyChallengeUseCase.execute(publicationDate, difficulty, playerId);
        return ResponseEntity.ok(DailyChallengeGameResponseMapper.toDTO(dailyChallengeGameDTO));
    }

    @GetMapping("/{dailyChallengeId}")
    public ResponseEntity<DailyChallengeGameResponseDTO> getDailyChallenge(@PathVariable UUID dailyChallengeId, @RequestHeader UUID playerId) {
        DailyChallengeGameDTO dailyChallengeGameDTO = getDailyChallengeUseCase.execute(dailyChallengeId, playerId);
        return ResponseEntity.ok(DailyChallengeGameResponseMapper.toDTO(dailyChallengeGameDTO));
    }

}
