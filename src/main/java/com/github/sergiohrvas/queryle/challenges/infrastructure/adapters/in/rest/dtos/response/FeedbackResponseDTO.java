package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.dtos.response;

import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.MatchLevel;

public record FeedbackResponseDTO(
    MatchLevel columnsNamesMatchLevel,
    MatchLevel columnsCountMatchLevel,
    MatchLevel rowsCountMatchLevel,
    MatchLevel rowsDataMatchLevel,
    MatchLevel rowsOrderMatchLevel
) {

}
