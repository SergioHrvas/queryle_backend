package com.github.sergiohrvas.queryle.challenges.domain.repositories;

import java.util.Optional;
import java.util.UUID;

import com.github.sergiohrvas.queryle.challenges.domain.models.DataContext;

public interface DataContextRepository {
    DataContext save(DataContext dataContext);
    Optional<DataContext> findById(UUID id);
}
