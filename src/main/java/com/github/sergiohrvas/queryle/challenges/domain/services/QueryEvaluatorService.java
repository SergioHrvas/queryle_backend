package com.github.sergiohrvas.queryle.challenges.domain.services;

import org.springframework.stereotype.Service;

import com.github.sergiohrvas.queryle.challenges.domain.exceptions.InvalidDataContextException;
import com.github.sergiohrvas.queryle.challenges.domain.models.DataContext;
import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.Feedback;
import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.Schema;
import com.github.sergiohrvas.queryle.challenges.domain.ports.SqlQueryExecutorPort;
import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.Dataset;
import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.MatchLevel;
import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.Query;
import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.QueryEvaluation;
import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.QueryExecutionPair;
import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.QueryExecutionStatus;
import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.QueryResult;

@Service
public class QueryEvaluatorService {
    private final SqlQueryExecutorPort executor;
    private final QueryResultMatcherService matcher;

    public QueryEvaluatorService(SqlQueryExecutorPort executor, QueryResultMatcherService matcher) {
        this.executor = executor;
        this.matcher = matcher;
    }

    public QueryEvaluation evaluate(
            Query submittedQuery,
            Query expectedQuery,
            DataContext dataContext) {
        Schema schema = dataContext.getSchema();
    
        Feedback publicFeedback = evaluateDataset(
            submittedQuery,
            expectedQuery,
            schema,
            dataContext.getPublicDataset()
        );

        if (!publicFeedback.isAllExact()) {
            return new QueryEvaluation(publicFeedback, false);
        }

        boolean validationPassed = validateAgainstPrivateDatasets(
                submittedQuery,
                expectedQuery,
                dataContext);

        return new QueryEvaluation(
                publicFeedback,
                validationPassed);
    }

    private Feedback noMatch() {
        return new Feedback(
                MatchLevel.NONE,
                MatchLevel.NONE,
                MatchLevel.NONE,
                MatchLevel.NONE,
                MatchLevel.NONE);
    }

    private boolean validateAgainstPrivateDatasets(
            Query submittedQuery,
            Query expectedQuery,
            DataContext dataContext) {
                
        for (Dataset privateDataset : dataContext.getPrivateDatasets()) {
            Feedback feedback = evaluateDataset(
                submittedQuery,
                expectedQuery,
                dataContext.getSchema(),
                privateDataset
            );

            if (!feedback.isAllExact()) {
                return false;
            }
        }
        return true;
    }

    private Feedback evaluateDataset(
            Query submittedQuery,
            Query expectedQuery,
            Schema schema,
            Dataset dataset) {

        QueryExecutionPair result = executor.execute(
                submittedQuery,
                expectedQuery,
                schema,
                dataset);

        QueryResult expected = result.expected();
        QueryResult actual = result.actual();

        if (expected.status() == QueryExecutionStatus.INVALID_QUERY) {
            throw new InvalidDataContextException(
                    "The expected query is invalid for the given data context.");
        }

        if (actual.status() == QueryExecutionStatus.INVALID_QUERY) {
            return noMatch();
        }

        return matcher.match(actual, expected);
    }
}