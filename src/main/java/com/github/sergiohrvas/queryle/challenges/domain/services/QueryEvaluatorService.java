package com.github.sergiohrvas.queryle.challenges.domain.services;

import org.springframework.stereotype.Service;

import com.github.sergiohrvas.queryle.challenges.domain.exceptions.InvalidDataContextException;
import com.github.sergiohrvas.queryle.challenges.domain.models.DataContext;
import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.Feedback;
import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.MatchLevel;
import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.Query;
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

    public Feedback evaluate (
        Query submittedQuery,
        Query expectedQuery,
        DataContext dataContext
    ){
        QueryResult expected = executor.execute(expectedQuery, dataContext);
        QueryResult actual = executor.execute(submittedQuery, dataContext);

        if(expected.status() == QueryExecutionStatus.INVALID_QUERY){
            throw new InvalidDataContextException("The expected query is invalid for the given data context.");
        }
        if (actual.status() == QueryExecutionStatus.INVALID_QUERY) {
            return new Feedback(
                MatchLevel.NONE,
                MatchLevel.NONE,
                MatchLevel.NONE,
                MatchLevel.NONE,
                MatchLevel.NONE
            );
        }

        return matcher.match(actual, expected);
    }
}
