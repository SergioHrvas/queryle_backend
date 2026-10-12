package com.github.sergiohrvas.queryle.challenges.domain.ports;

import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.QueryResult;
import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.Query;

import com.github.sergiohrvas.queryle.challenges.domain.models.DataContext;

public interface SqlQueryExecutorPort {
    QueryResult execute(Query query, DataContext dataContext);
}
