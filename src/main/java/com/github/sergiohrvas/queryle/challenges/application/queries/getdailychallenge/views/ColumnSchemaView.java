package com.github.sergiohrvas.queryle.challenges.application.queries.getdailychallenge.views;

public record ColumnSchemaView(
    String name,
    String type,
    boolean isNullable,
    boolean isPrimaryKey,
    int position
) {

}