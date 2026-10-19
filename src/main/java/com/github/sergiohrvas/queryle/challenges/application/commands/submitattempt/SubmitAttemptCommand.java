package com.github.sergiohrvas.queryle.challenges.application.commands.submitattempt;

public record SubmitAttemptCommand(
    String attempt,
    String playerId,
    String dailyChallengeId
){
}
