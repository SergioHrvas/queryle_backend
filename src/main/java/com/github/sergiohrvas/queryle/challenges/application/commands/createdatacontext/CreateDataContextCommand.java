package com.github.sergiohrvas.queryle.challenges.application.commands.createdatacontext;

import java.util.List;

public record CreateDataContextCommand(
    String name,
    String description,
    String schema,
    String publicDataset,
    List<String> privateDatasets
) {
    
}
