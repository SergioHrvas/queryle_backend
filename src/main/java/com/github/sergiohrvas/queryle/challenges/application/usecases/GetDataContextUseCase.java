package com.github.sergiohrvas.queryle.challenges.application.usecases;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.github.sergiohrvas.queryle.challenges.domain.models.DataContext;
import com.github.sergiohrvas.queryle.challenges.domain.ports.DataContextPort;

@Service
public class GetDataContextUseCase {
    private final DataContextPort dataContextPort;

    public GetDataContextUseCase(DataContextPort dataContextPort) {
        this.dataContextPort = dataContextPort;
    }

    public DataContext execute(UUID dataContextId) {
        return dataContextPort.findById(dataContextId).orElseThrow(() -> new IllegalArgumentException("Data context not found"));
    }
}
