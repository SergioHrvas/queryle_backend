package com.github.sergiohrvas.queryle.challenges.domain.ports;

import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.QueryResult;
import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.Schema;
import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.Dataset;
import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.Query;

public interface SqlQueryExecutorPort {
    QueryResult execute(Query query, Schema schema, Dataset dataset);
}
