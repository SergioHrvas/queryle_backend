package com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.github.sergiohrvas.queryle.challenges.domain.exceptions.InvalidQueryResult;

/**
 * A record representing the result of a query execution.
 */
public record QueryResult(
        List<String> columns,
        List<Map<String, String>> rows,
        QueryExecutionStatus status
) {
    public QueryResult {
        if(Objects.isNull(columns)){
            throw new InvalidQueryResult("Columns cannot be null");
        }
        if(Objects.isNull(rows)){
            throw new InvalidQueryResult("Rows cannot be null");
        }
        if(Objects.isNull(status)){
            throw new InvalidQueryResult("Status cannot be null");
        }

        columns = List.copyOf(columns);
        rows = rows.stream().map(Map::copyOf).toList();
    }
}