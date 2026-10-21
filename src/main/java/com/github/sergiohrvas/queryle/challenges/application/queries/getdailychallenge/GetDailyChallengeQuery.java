package com.github.sergiohrvas.queryle.challenges.application.queries.getdailychallenge;

import java.util.UUID;

public record GetDailyChallengeQuery(
    UUID playerId,
    UUID challengeId
) {
}