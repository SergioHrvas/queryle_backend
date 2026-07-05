package com.github.sergiohrvas.queryle.challenges.domain.ports;

import java.util.Optional;
import java.util.UUID;

import com.github.sergiohrvas.queryle.challenges.domain.models.DataContext;

public interface DataContextPort {
    void save(DataContext dataContext);
    Optional<DataContext> findById(UUID id);
}
