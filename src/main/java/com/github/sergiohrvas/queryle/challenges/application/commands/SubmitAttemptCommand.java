package com.github.sergiohrvas.queryle.challenges.application.commands;

public record SubmitAttemptCommand(
    String attempt,
    String playerId,
    String dailyChallengeId
){
}
