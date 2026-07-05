package com.github.sergiohrvas.queryle.challenges.infrastructure.controllers;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.sergiohrvas.queryle.challenges.application.dtos.DataContextResponseDTO;
import com.github.sergiohrvas.queryle.challenges.application.mappers.DataContextMapper;
import com.github.sergiohrvas.queryle.challenges.application.usecases.GetDataContextUseCase;
import com.github.sergiohrvas.queryle.challenges.domain.models.DataContext;

@RestController
@RequestMapping("/api/v1/data-contexts")
public class DataContextController {
    private final GetDataContextUseCase getDataContextUseCase;

    public DataContextController(GetDataContextUseCase getDataContextUseCase) {
        this.getDataContextUseCase = getDataContextUseCase;
    }

    @GetMapping("/{dataContextId}")
    public ResponseEntity<DataContextResponseDTO> getDataContext(@PathVariable UUID dataContextId) {
        DataContext dataContext = getDataContextUseCase.execute(dataContextId);
        return ResponseEntity.ok(DataContextMapper.toDTO(dataContext));
    }

}
