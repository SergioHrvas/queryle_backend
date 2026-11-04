package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.out.persistence.datacontext;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.github.sergiohrvas.queryle.challenges.application.ports.DataContextProjectionPort;
import com.github.sergiohrvas.queryle.challenges.application.projections.datacontext.DataContextProjection;
import com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.out.persistence.datacontext.repositories.DataContextProjectionRepository;

@Component 
public class DataContextProjectionAdapter implements DataContextProjectionPort {
    private final DataContextProjectionRepository repository;

    public DataContextProjectionAdapter(DataContextProjectionRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(DataContextProjection dataContextProjection) {
        repository.save(dataContextProjection);
    }

    @Override
    public Optional<DataContextProjection> findByDataContextId(UUID dataContextId) {
        return repository.findByDataContextId(dataContextId);
    }

    @Override
    public void deleteByDataContextId(UUID dataContextId) {
        repository.deleteByDataContextId(dataContextId);
    }
}
