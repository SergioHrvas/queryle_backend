package com.github.sergiohrvas.queryle.challenges.application.queries.getdailychallenge.views;

import java.util.List;

public record DatasetTableView(
    String name,
    List<String> columns,
    List<List<Object>> rows
) {
}   