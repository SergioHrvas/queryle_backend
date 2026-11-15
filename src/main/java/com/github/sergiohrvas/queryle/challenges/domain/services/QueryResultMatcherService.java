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
        MatchLevel rowsDataMatchLevel = evaluateRowsDataMatchLevel(
            actual.rows(),
            expected.rows(),
            actual.columns(),
            expected.columns(),
            actual.sourceColumns(),
            expected.sourceColumns()
        );
        MatchLevel rowsOrderMatchLevel = evaluateRowsOrderMatchLevel(
            actual.rows(),
            expected.rows(),
            actual.columns(),
            expected.columns(),
            actual.sourceColumns(),
            expected.sourceColumns()
        );
        MatchLevel columnsOrderMatchLevel = evaluateColumnsOrderMatchLevel(
            actual.sourceColumns(),
            expected.sourceColumns()
        );

        return new Feedback(
            columnsNamesMatchLevel,
            columnsCountMatchLevel,
            columnsOrderMatchLevel,
            rowsCountMatchLevel,
            rowsDataMatchLevel,
            rowsOrderMatchLevel
        );
    }

    private MatchLevel evaluateColumnsCountMatchLevel(List<String> columns, List<String> columns2) {
        return columns.size() == columns2.size()
            ? MatchLevel.EXACT
            : MatchLevel.NONE;
    }

    private MatchLevel evaluateColumnsNamesMatchLevel(List<String> columns, List<String> columns2) {
        if (columns.isEmpty() || columns2.isEmpty()) {
            return MatchLevel.NONE;
        }
        Set<String> actualColumns = columns.stream().collect(Collectors.toSet());
        Set<String> expectedColumns = columns2.stream().collect(Collectors.toSet());

        if (actualColumns.containsAll(expectedColumns)) {
            return MatchLevel.EXACT;
        }

        boolean hasAnyMatch = actualColumns.stream().anyMatch(expectedColumns::contains);

        return hasAnyMatch ? MatchLevel.PARTIAL : MatchLevel.NONE;
    }

    private MatchLevel evaluateColumnsOrderMatchLevel(List<String> columns, List<String> columns2) {
        if (columns.isEmpty() || columns2.isEmpty()) {
            return MatchLevel.NONE;
        }
        if (columns.equals(columns2)) {
            return MatchLevel.EXACT;
        }

        List<String> shorter = columns.size() <= columns2.size() ? columns : columns2;
        List<String> longer = columns.size() <= columns2.size() ? columns2 : columns;

        return isRelativeOrderColumnPreserved(shorter, longer) ? MatchLevel.PARTIAL : MatchLevel.NONE;
    }

    private boolean isRelativeOrderColumnPreserved(List<String> subsequence, List<String> sequence) {
        int fromIndex = 0;
        for (String column : subsequence) {
            int foundAt = -1;
            for (int i = fromIndex; i < sequence.size(); i++) {
                if (sequence.get(i).equals(column)) {
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
    
    private MatchLevel evaluateRowsCountMatchLevel(int userRowCount, int expectedRowCount) {
        return userRowCount == expectedRowCount
            ? MatchLevel.EXACT
            : MatchLevel.NONE;
    }

    private MatchLevel evaluateRowsDataMatchLevel(
        List<Map<String, String>> userRows,
        List<Map<String, String>> expectedRows,
        List<String> actualColumns,
        List<String> expectedColumns,
        List<String> actualSourceColumns,
        List<String> expectedSourceColumns) {
        if (userRows.isEmpty() || expectedRows.isEmpty()) {
            return MatchLevel.NONE;
        }
        if (!canProjectRows(userRows, actualColumns, expectedColumns, actualSourceColumns, expectedSourceColumns)) {
            return MatchLevel.NONE;
        }

        Set<Map<String, String>> userSet = projectRows(
            userRows,
            actualColumns,
            expectedColumns,
            actualSourceColumns,
            expectedSourceColumns
        );
        Set<Map<String, String>> expectedSet = expectedRows.stream().collect(Collectors.toSet());

        if (userSet.equals(expectedSet)) {
            return MatchLevel.EXACT;
        }

        boolean hasCommonRows = userSet.stream().anyMatch(expectedSet::contains);
        return hasCommonRows ? MatchLevel.PARTIAL : MatchLevel.NONE;
    }

    private MatchLevel evaluateRowsOrderMatchLevel(
        List<Map<String, String>> userRows, 
        List<Map<String, String>> expectedRows,
        List<String> actualColumns,
        List<String> expectedColumns,
        List<String> actualSourceColumns,
        List<String> expectedSourceColumns) {

        if (userRows.isEmpty() || expectedRows.isEmpty()) {
            return MatchLevel.NONE;
        }
        if (!canProjectRows(userRows, actualColumns, expectedColumns, actualSourceColumns, expectedSourceColumns)) {
            return MatchLevel.NONE;
        }

        List<Map<String, String>> projectedUserRows = projectRowsInOrder(
            userRows,
            actualColumns,
            expectedColumns,
            actualSourceColumns,
            expectedSourceColumns
        );

        if (projectedUserRows.equals(expectedRows)) {
            return MatchLevel.EXACT;
        }

        List<Map<String, String>> shorter = projectedUserRows.size() <= expectedRows.size()
            ? projectedUserRows
            : expectedRows;
        List<Map<String, String>> longer = projectedUserRows.size() <= expectedRows.size()
            ? expectedRows
            : projectedUserRows;

        return isRelativeOrderRowPreserved(shorter, longer) ? MatchLevel.PARTIAL : MatchLevel.NONE;
    }

    private Set<Map<String, String>> projectRows(
        List<Map<String, String>> rows,
        List<String> actualColumns,
        List<String> expectedColumns,
        List<String> actualSourceColumns,
        List<String> expectedSourceColumns) {
        return rows.stream()
            .map(row -> projectRow(
                row,
                actualColumns,
                expectedColumns,
                actualSourceColumns,
                expectedSourceColumns
            ))
            .collect(Collectors.toSet());
    }

    private List<Map<String, String>> projectRowsInOrder(
        List<Map<String, String>> rows,
        List<String> actualColumns,
        List<String> expectedColumns,
        List<String> actualSourceColumns,
        List<String> expectedSourceColumns) {
        return rows.stream()
            .map(row -> projectRow(
                row,
                actualColumns,
                expectedColumns,
                actualSourceColumns,
                expectedSourceColumns
            ))
            .toList();
    }

    private Map<String, String> projectRow(
        Map<String, String> row,
        List<String> actualColumns,
        List<String> expectedColumns,
        List<String> actualSourceColumns,
        List<String> expectedSourceColumns) {
        return expectedColumns.stream().collect(Collectors.toMap(
            column -> column,
            column -> valueForExpectedColumn(
                row,
                column,
                actualColumns,
                actualSourceColumns,
                expectedSourceColumns,
                expectedColumns.indexOf(column)
            )
        ));
    }

    private boolean canProjectRows(
        List<Map<String, String>> rows,
        List<String> actualColumns,
        List<String> expectedColumns,
        List<String> actualSourceColumns,
        List<String> expectedSourceColumns) {
        return rows.stream().allMatch(row -> expectedColumns.stream().allMatch(column ->
            valueForExpectedColumn(
                row,
                column,
                actualColumns,
                actualSourceColumns,
                expectedSourceColumns,
                expectedColumns.indexOf(column)
            ) != null
        ));
    }

    private String valueForExpectedColumn(
        Map<String, String> row,
        String expectedColumn,
        List<String> actualColumns,
        List<String> actualSourceColumns,
        List<String> expectedSourceColumns,
        int expectedIndex) {
        String expectedSourceColumn = expectedSourceColumns.get(expectedIndex);
        int actualIndex = actualSourceColumns.indexOf(expectedSourceColumn);
        if (actualIndex >= 0 && actualIndex < actualColumns.size()) {
            return row.get(actualColumns.get(actualIndex));
        }
        return row.get(expectedColumn);
    }

    private boolean isRelativeOrderRowPreserved(
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