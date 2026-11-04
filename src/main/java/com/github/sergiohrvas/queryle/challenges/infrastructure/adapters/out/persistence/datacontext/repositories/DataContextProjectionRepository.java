package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.out.persistence.datacontext.repositories;

import java.util.UUID;
import java.util.Optional;

import com.github.sergiohrvas.queryle.challenges.application.projections.datacontext.DataContextProjection;

public interface DataContextProjectionRepository {
    void save(DataContextProjection dataContextProjection);

    Optional<DataContextProjection> findByDataContextId(UUID dataContextId);

    void deleteByDataContextId(UUID dataContextId);    
}
