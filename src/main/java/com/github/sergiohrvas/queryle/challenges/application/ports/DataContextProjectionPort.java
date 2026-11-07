package com.github.sergiohrvas.queryle.challenges.application.ports;

import java.util.Optional;
import java.util.UUID;

import com.github.sergiohrvas.queryle.challenges.application.projections.datacontext.DataContextProjection;

public interface DataContextProjectionPort {
    void save(DataContextProjection dataContextProjection);

    Optional<DataContextProjection> findByDataContextId(UUID dataContextId);

    void deleteByDataContextId(UUID dataContextId);
}