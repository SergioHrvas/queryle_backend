package com.github.sergiohrvas.queryle.challenges.domain.services;

import org.springframework.stereotype.Service;

import java.util.List;

import com.github.sergiohrvas.queryle.challenges.domain.exceptions.InvalidDataContextException;
import com.github.sergiohrvas.queryle.challenges.domain.models.DataContext;
import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.Feedback;
import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.Schema;
import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.Dataset;
import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.MatchLevel;
import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.Query;
import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.QueryEvaluation;
import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.QueryExecutionStatus;
import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.QueryResult;
import com.github.sergiohrvas.queryle.challenges.domain.ports.SqlQueryExecutorPort;

@Service 
public class QueryEvaluatorService {
    private final SqlQueryExecutorPort executor;
    private final QueryResultMatcherService matcher;

    public QueryEvaluatorService(SqlQueryExecutorPort executor, QueryResultMatcherService matcher) {
        this.executor = executor;
        this.matcher = matcher;
    }

    public QueryEvaluation evaluate (
        Query submittedQuery,
        Query expectedQuery,
        DataContext dataContext
    ){
        Schema schema = dataContext.getSchema();
        Dataset publicDataset = dataContext.getPublicDataset();

        QueryResult expected = executor.execute(expectedQuery, schema, publicDataset);
        QueryResult actual = executor.execute(submittedQuery, schema, publicDataset);

        if(expected.status() == QueryExecutionStatus.INVALID_QUERY){
            throw new InvalidDataContextException("The expected query is invalid for the given data context.");
        }
        if (actual.status() == QueryExecutionStatus.INVALID_QUERY) {
            return new QueryEvaluation(noMatch(), false);
        }

        Feedback feedback = matcher.match(actual, expected);

        boolean validationPassed = validateAgainstPrivateDatasets(
            submittedQuery,
            expectedQuery,
            dataContext
        );

        return new QueryEvaluation(
            feedback,
            validationPassed
        );
    }

    private Feedback noMatch() {
        return new Feedback(
            MatchLevel.NONE,
            MatchLevel.NONE,
            MatchLevel.NONE,
            MatchLevel.NONE,
            MatchLevel.NONE
        );
    }
    
    private boolean validateAgainstPrivateDatasets(
        Query submittedQuery,
        Query expectedQuery,
        DataContext dataContext
    ) {
        Schema schema = dataContext.getSchema();
        List<Dataset> privateDatasets = dataContext.getPrivateDatasets();

        for (Dataset privateDataset : privateDatasets) {
            QueryResult expected = executor.execute(expectedQuery, schema, privateDataset);
            QueryResult actual = executor.execute(submittedQuery, schema, privateDataset);

            if(expected.status() == QueryExecutionStatus.INVALID_QUERY){
                throw new InvalidDataContextException("The expected query is invalid for the given data context.");
            }
            if (actual.status() == QueryExecutionStatus.INVALID_QUERY) {
                return false;
            }

            Feedback feedback = matcher.match(actual, expected);
            if (!feedback.isAllExact()) {
                return false;
            }
        }

        return true;
    }
}
