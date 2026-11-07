package com.github.sergiohrvas.queryle.challenges.application.queries.getdailychallenge.views;

import java.util.List;

public record TableSchemaView(
    String name,
    List<ColumnSchemaView> columns
) {
}