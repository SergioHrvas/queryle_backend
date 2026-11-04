package com.github.sergiohrvas.queryle.challenges.application.queries.getdailychallenge.views;

public record DailyChallengeDataContextView (
    String name,
    String description,
    SchemaView schema,
    DatasetView dataset
)
{ }
