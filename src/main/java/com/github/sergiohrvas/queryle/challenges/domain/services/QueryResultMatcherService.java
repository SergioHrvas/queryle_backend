package com.github.sergiohrvas.queryle.challenges.domain.services;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.Feedback;
import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.MatchLevel;
import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.QueryResult;

@Service
public class QueryResultMatcherService {

    public Feedback match(QueryResult actual, QueryResult expected) {
        MatchLevel columnsCountMatchLevel = evaluateColumnsCountMatchLevel(actual.columns(), expected.columns());
        MatchLevel columnsNamesMatchLevel = evaluateColumnsNamesMatchLevel(actual.columns(), expected.columns());
        MatchLevel rowsCountMatchLevel = evaluateRowsCountMatchLevel(actual.rows().size(), expected.rows().size());
        MatchLevel rowsDataMatchLevel = evaluateRowsDataMatchLevel(actual.rows(), expected.rows());
        MatchLevel rowsOrderMatchLevel = evaluateRowsOrderMatchLevel(actual.rows(), expected.rows());

        return new Feedback(
            columnsNamesMatchLevel,
            columnsCountMatchLevel,
            rowsCountMatchLevel,
            rowsDataMatchLevel,
            rowsOrderMatchLevel
        );
    }

    private MatchLevel evaluateColumnsCountMatchLevel(List<String> columns, List<String> columns2) {
        if (columns.isEmpty() || columns2.isEmpty()) {
            return MatchLevel.NONE;
        }
        return columns.size() == columns2.size() ? MatchLevel.EXACT : MatchLevel.NONE;
    }

    private MatchLevel evaluateColumnsNamesMatchLevel(List<String> columns, List<String> columns2) {
        if (columns.isEmpty() || columns2.isEmpty()) {
            return MatchLevel.NONE;
        }
        if (columns.equals(columns2)) {
            return MatchLevel.EXACT;
        }

        Set<String> expectedSet = columns2.stream().collect(Collectors.toSet());
        boolean hasAnyMatch = columns.stream().anyMatch(expectedSet::contains);

        return hasAnyMatch ? MatchLevel.PARTIAL : MatchLevel.NONE;
    }

    private MatchLevel evaluateRowsCountMatchLevel(int userRowCount, int expectedRowCount) {
        if (userRowCount == expectedRowCount) {
            return MatchLevel.EXACT;
        }
        return userRowCount > 0 ? MatchLevel.PARTIAL : MatchLevel.NONE;
    }

    private MatchLevel evaluateRowsDataMatchLevel(List<Map<String, String>> userRows, List<Map<String, String>> expectedRows) {
        if (userRows.isEmpty() || expectedRows.isEmpty()) {
            return MatchLevel.NONE;
        }

        Set<Map<String, String>> userSet = userRows.stream().collect(Collectors.toSet());
        Set<Map<String, String>> expectedSet = expectedRows.stream().collect(Collectors.toSet());

        if (userSet.equals(expectedSet)) {
            return MatchLevel.EXACT;
        }

        boolean hasCommonRows = userSet.stream().anyMatch(expectedSet::contains);
        return hasCommonRows ? MatchLevel.PARTIAL : MatchLevel.NONE;
    }

    private MatchLevel evaluateRowsOrderMatchLevel(
        List<Map<String, String>> userRows, 
        List<Map<String, String>> expectedRows) {

        if (userRows.isEmpty() || expectedRows.isEmpty()) {
            return MatchLevel.NONE;
        }

        if (userRows.equals(expectedRows)) {
            return MatchLevel.EXACT;
        }

        List<Map<String, String>> shorter = userRows.size() <= expectedRows.size() ? userRows : expectedRows;
        List<Map<String, String>> longer = userRows.size() <= expectedRows.size() ? expectedRows : userRows;

        return isRelativeOrderPreserved(shorter, longer) ? MatchLevel.PARTIAL : MatchLevel.NONE;
    }

    private boolean isRelativeOrderPreserved(
            List<Map<String, String>> subsequence,
            List<Map<String, String>> sequence) {
        int fromIndex = 0;
        for (Map<String, String> row : subsequence) {
            int foundAt = -1;
            for (int i = fromIndex; i < sequence.size(); i++) {
                if (sequence.get(i).equals(row)) {
                    foundAt = i;
                    break;
                }
            }
            if (foundAt == -1) {
                return false;
            }
            fromIndex = foundAt + 1;
        }
        return true;
    }
}
