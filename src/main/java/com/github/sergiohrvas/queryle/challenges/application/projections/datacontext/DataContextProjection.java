package com.github.sergiohrvas.queryle.challenges.application.projections.datacontext;

import java.util.UUID;

import com.github.sergiohrvas.queryle.challenges.application.queries.getdailychallenge.views.DatasetView;
import com.github.sergiohrvas.queryle.challenges.application.queries.getdailychallenge.views.SchemaView;

public record DataContextProjection(
    UUID dataContextId,
    SchemaView schema,
    DatasetView dataset,
    long version
) {
    
}
