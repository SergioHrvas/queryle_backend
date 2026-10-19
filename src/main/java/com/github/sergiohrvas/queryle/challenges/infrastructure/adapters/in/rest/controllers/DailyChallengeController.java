package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.controllers;

import java.util.UUID;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.ResponseEntity;

import com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.dtos.request.SubmitAttemptRequestDTO;
import com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.dtos.response.GameResponseDTO;
import com.github.sergiohrvas.queryle.challenges.application.commands.SubmitAttemptCommand;
import com.github.sergiohrvas.queryle.challenges.application.commands.SubmitAttemptCommandHandler;

@RestController
@RequestMapping("/api/v1/challenges")
public class DailyChallengeController {
    private final SubmitAttemptCommandHandler submitAttemptCommandHandler;

    public DailyChallengeController(SubmitAttemptCommandHandler submitAttemptCommandHandler) {
        this.submitAttemptCommandHandler = submitAttemptCommandHandler;
    }

    @PostMapping("/{dailyChallengeId}/submit-attempt")
    public ResponseEntity<GameResponseDTO> submitAttempt(@PathVariable UUID dailyChallengeId, @RequestHeader UUID playerId, @RequestBody SubmitAttemptRequestDTO request) {
        SubmitAttemptCommand command = new SubmitAttemptCommand(request.query(), playerId.toString(), dailyChallengeId.toString());
        submitAttemptCommandHandler.handle(command);
        return ResponseEntity.ok().build();
    }
}