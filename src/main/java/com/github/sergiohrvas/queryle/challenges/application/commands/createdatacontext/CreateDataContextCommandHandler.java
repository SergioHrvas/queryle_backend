package com.github.sergiohrvas.queryle.challenges.application.commands.createdatacontext;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.github.sergiohrvas.queryle.challenges.application.ports.DataContextProjectionGeneratorPort;
import com.github.sergiohrvas.queryle.challenges.application.ports.DataContextProjectionPort;
import com.github.sergiohrvas.queryle.challenges.application.projections.datacontext.DataContextProjection;
import com.github.sergiohrvas.queryle.challenges.domain.models.DataContext;
import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.Dataset;
import com.github.sergiohrvas.queryle.challenges.domain.models.valueobjects.Schema;
import com.github.sergiohrvas.queryle.challenges.domain.repositories.DataContextRepository;

import jakarta.transaction.Transactional;

@Service 
public class CreateDataContextCommandHandler {
    private final DataContextProjectionGeneratorPort projectionGenerator;
    private final DataContextProjectionPort dataContextProjection;
    private final DataContextRepository dataContextRepository;
    
    public CreateDataContextCommandHandler(
        DataContextProjectionGeneratorPort projectionGenerator,
        DataContextProjectionPort dataContextProjection,
        DataContextRepository dataContextRepository
    ) {
        this.projectionGenerator = projectionGenerator;
        this.dataContextProjection = dataContextProjection;
        this.dataContextRepository = dataContextRepository;
    }

    @Transactional
    public void handle(CreateDataContextCommand command) {
        UUID dataContextId = dataContextRepository.generateId();

        Schema schema = new Schema(command.schema());
        Dataset publicDataset = new Dataset(command.publicDataset());
        List<Dataset> privateDatasets = command.privateDatasets().stream()
            .map(Dataset::new)
            .toList();

        DataContext dataContext = new DataContext(
            dataContextId,
            command.name(),
            command.description(),
            schema,
            publicDataset,
            privateDatasets
        );

        DataContextProjection projection = projectionGenerator.generate(dataContext);
        
        dataContextRepository.save(dataContext);
        dataContextProjection.save(projection);
    }
}
