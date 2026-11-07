package com.github.sergiohrvas.queryle.challenges.domain.ports;

import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.QueryExecutionPair;
import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.Schema;
import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.Dataset;
import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.Query;

public interface SqlQueryExecutorPort {
    QueryExecutionPair execute(
        Query submittedQuery,
        Query expectedQuery,
        Schema schema,
        Dataset dataset
    );
}
