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
import org.springframework.http.ResponseEntity;

import com.github.sergiohrvas.queryle.challenges.application.commands.createdailychallenge.CreateDailyChallengeCommand;
import com.github.sergiohrvas.queryle.challenges.application.commands.createdailychallenge.CreateDailyChallengeCommandHandler;
import com.github.sergiohrvas.queryle.challenges.application.commands.submitattempt.SubmitAttemptCommand;
import com.github.sergiohrvas.queryle.challenges.application.commands.submitattempt.SubmitAttemptCommandHandler;
import com.github.sergiohrvas.queryle.challenges.application.queries.getdailychallenge.GetDailyChallengeQuery;
import com.github.sergiohrvas.queryle.challenges.application.queries.getdailychallenge.GetDailyChallengeQueryHandler;
import com.github.sergiohrvas.queryle.challenges.application.queries.getdailychallenges.GetDailyChallengesQuery;
import com.github.sergiohrvas.queryle.challenges.application.queries.getdailychallenges.GetDailyChallengesQueryHandler;
import com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.dtos.request.DailyChallengeRequestDTO;
import com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.dtos.request.SubmitAttemptRequestDTO;
import com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.dtos.response.DailyChallengeSummaryResponseDTO;
import com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.dtos.response.DailyChallengeDetailResponseDTO;
import com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.mappers.DailyChallengeResponseMapper;

@RestController
@RequestMapping("/api/v1/dailychallenges")
public class DailyChallengeController {
    private final SubmitAttemptCommandHandler submitAttemptCommandHandler;
    private final CreateDailyChallengeCommandHandler createDailyChallengeCommandHandler;
    private final GetDailyChallengeQueryHandler getDailyChallengeQueryHandler;
    private final GetDailyChallengesQueryHandler getDailyChallengesQueryHandler;

    public DailyChallengeController(
        SubmitAttemptCommandHandler submitAttemptCommandHandler,
        CreateDailyChallengeCommandHandler createDailyChallengeCommandHandler,
        GetDailyChallengesQueryHandler getDailyChallengesQueryHandler,
        GetDailyChallengeQueryHandler getDailyChallengeQueryHandler
    ) {
        this.submitAttemptCommandHandler = submitAttemptCommandHandler;
        this.createDailyChallengeCommandHandler = createDailyChallengeCommandHandler;
        this.getDailyChallengesQueryHandler = getDailyChallengesQueryHandler;
        this.getDailyChallengeQueryHandler = getDailyChallengeQueryHandler;
    }

    @GetMapping
    public ResponseEntity<List<DailyChallengeSummaryResponseDTO>> getDailyChallenges(
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
        List<DailyChallengeSummaryResponseDTO> response = 
            getDailyChallengesQueryHandler.handle(query).stream()
                .map(DailyChallengeResponseMapper::toDTO).toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping ("/{dailyChallengeId}")
    public ResponseEntity<DailyChallengeDetailResponseDTO> getDailyChallenge(
        @PathVariable UUID dailyChallengeId,
        @RequestHeader("X-Player-Id") UUID playerId
    ) {
        GetDailyChallengeQuery query = new GetDailyChallengeQuery(
            playerId,
            dailyChallengeId
        );
        DailyChallengeDetailResponseDTO response = DailyChallengeResponseMapper.toDTO(
            getDailyChallengeQueryHandler.handle(query)
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{dailyChallengeId}/attempts")
    public ResponseEntity<Void> submitAttempt(
        @PathVariable UUID dailyChallengeId, 
        @RequestHeader("X-Player-Id") UUID playerId, 
        @RequestBody SubmitAttemptRequestDTO request
    ) {
        submitAttemptCommandHandler.handle(
            new SubmitAttemptCommand(
                request.query(), 
                playerId, 
                dailyChallengeId
            )
        );
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping
    public ResponseEntity<Void> createDailyChallenge(
        @RequestHeader("X-Player-Id") UUID playerId,
        @RequestBody DailyChallengeRequestDTO request
    ) {
        createDailyChallengeCommandHandler.handle(
            new CreateDailyChallengeCommand(
                request.formulation(),
                request.query(),
                request.difficulty(),
                request.publicationDate(),
                request.dataContextId()
            )
        );
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}