package com.github.sergiohrvas.queryle.challenges.application.commands.submitattempt;

import java.util.UUID;

public record SubmitAttemptCommand(
    String rawQuery,
    UUID playerId,
    UUID dailyChallengeId
){
}
