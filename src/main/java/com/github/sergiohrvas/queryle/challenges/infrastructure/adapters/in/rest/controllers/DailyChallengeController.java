package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import com.github.sergiohrvas.queryle.challenges.application.commands.submitattempt.SubmitAttemptCommand;
import com.github.sergiohrvas.queryle.challenges.application.commands.submitattempt.SubmitAttemptCommandHandler;
import com.github.sergiohrvas.queryle.challenges.application.queries.getdailychallenges.GetDailyChallengesQuery;
import com.github.sergiohrvas.queryle.challenges.application.queries.getdailychallenges.GetDailyChallengesQueryHandler;
import com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.dtos.request.SubmitAttemptRequestDTO;
import com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.dtos.response.DailyChallengeResponseDTO;
import com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.mappers.DailyChallengeResponseMapper;

@RestController
@RequestMapping("/api/v1/challenges")
public class DailyChallengeController {
    private final SubmitAttemptCommandHandler submitAttemptCommandHandler;
    private final GetDailyChallengesQueryHandler getDailyChallengesQueryHandler;

    public DailyChallengeController(
        SubmitAttemptCommandHandler submitAttemptCommandHandler,
        GetDailyChallengesQueryHandler getDailyChallengesQueryHandler
    ) {
        this.submitAttemptCommandHandler = submitAttemptCommandHandler;
        this.getDailyChallengesQueryHandler = getDailyChallengesQueryHandler;
    }

    @GetMapping
    public ResponseEntity<List<DailyChallengeResponseDTO>> getDailyChallenges(
        @RequestHeader("X-Player-Id") UUID playerId,
        @RequestParam String difficulty,
        @RequestParam LocalDate dateFrom,
        @RequestParam LocalDate dateTo
    ) {
        GetDailyChallengesQuery query = new GetDailyChallengesQuery(
            playerId,
            difficulty,
            dateFrom,
            dateTo
        );
        List<DailyChallengeResponseDTO> response = 
            getDailyChallengesQueryHandler.handle(query).stream()
                .map(DailyChallengeResponseMapper::toDTO).toList();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{dailyChallengeId}/attempts")
    public ResponseEntity<Void> submitAttempt(
        @PathVariable UUID dailyChallengeId, 
        @RequestHeader("X-Player-Id") UUID playerId, 
        @RequestBody SubmitAttemptRequestDTO request
    ) {
        SubmitAttemptCommand command = new SubmitAttemptCommand(
            request.query(), 
            playerId, 
            dailyChallengeId
        );
        submitAttemptCommandHandler.handle(command);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}