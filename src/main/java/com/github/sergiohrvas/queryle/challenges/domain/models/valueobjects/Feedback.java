package com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects;

import java.util.Objects;

import com.github.sergiohrvas.queryle.challenges.domain.exceptions.InvalidFeedbackException;

public record Feedback (
    MatchLevel columnsNamesMatchLevel,
    MatchLevel columnsCountMatchLevel,
    MatchLevel rowsCountMatchLevel,
    MatchLevel rowsDataMatchLevel,
    MatchLevel rowsOrderMatchLevel
) {
    public Feedback {
        if(Objects.isNull(columnsNamesMatchLevel)){
            throw new InvalidFeedbackException("Columns names match level cannot be null");
        }
        if(Objects.isNull(columnsCountMatchLevel)){
            throw new InvalidFeedbackException("Columns count match level cannot be null");
        }
        if(Objects.isNull(rowsCountMatchLevel)){
            throw new InvalidFeedbackException("Rows count match level cannot be null");
        }
        if(Objects.isNull(rowsDataMatchLevel)){
            throw new InvalidFeedbackException("Rows data match level cannot be null");
        }
        if(Objects.isNull(rowsOrderMatchLevel)){
            throw new InvalidFeedbackException("Rows order match level cannot be null");
        }
    }

    public boolean isAllExact() {
        return MatchLevel.EXACT.equals(columnsNamesMatchLevel) &&
            MatchLevel.EXACT.equals(columnsCountMatchLevel) &&
            MatchLevel.EXACT.equals(rowsCountMatchLevel) &&
            MatchLevel.EXACT.equals(rowsDataMatchLevel) &&
            MatchLevel.EXACT.equals(rowsOrderMatchLevel);
    }
}
