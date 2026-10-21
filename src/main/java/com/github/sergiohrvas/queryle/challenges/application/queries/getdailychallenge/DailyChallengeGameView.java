package com.github.sergiohrvas.queryle.challenges.application.queries.getdailychallenge;

import java.util.List;

public record DailyChallengeGameView(
    String status,
    List<DailyChallengeAttemptView> attempts
) {
    
}
