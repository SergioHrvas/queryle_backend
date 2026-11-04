package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.sergiohrvas.queryle.challenges.application.commands.createdatacontext.CreateDataContextCommand;
import com.github.sergiohrvas.queryle.challenges.application.commands.createdatacontext.CreateDataContextCommandHandler;
import com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.dtos.request.DataContextRequestDTO;

@RestController
@RequestMapping("/api/v1/datacontexts")
public class DataContextController {
    
    private final CreateDataContextCommandHandler createDataContextCommandHandler;

    public DataContextController(CreateDataContextCommandHandler createDataContextCommandHandler) {
        this.createDataContextCommandHandler = createDataContextCommandHandler;
    }

    @PostMapping
    public ResponseEntity<Void> createDataContext(@RequestBody DataContextRequestDTO request) {
        createDataContextCommandHandler.handle(
            new CreateDataContextCommand(
                request.name(),
                request.description(),
                request.schema(),
                request.publicDataset(),
                request.privateDatasets()
            )
        );
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}