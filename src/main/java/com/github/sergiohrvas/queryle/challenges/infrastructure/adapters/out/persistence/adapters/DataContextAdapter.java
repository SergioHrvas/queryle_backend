package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.out.persistence.adapters;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.github.sergiohrvas.queryle.challenges.domain.models.DataContext;
import com.github.sergiohrvas.queryle.challenges.domain.ports.DataContextPort;
import com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.out.persistence.mappers.DataContextEntityMapper;
import com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.out.persistence.repositories.DataContextJPARepository;

@Component
public class DataContextAdapter implements DataContextPort {
    
    private final DataContextJPARepository dataContextJPARepository;

    public DataContextAdapter(DataContextJPARepository dataContextJPARepository) {
        this.dataContextJPARepository = dataContextJPARepository;
    }

    @Override
    public DataContext save(DataContext dataContext) {
        return DataContextEntityMapper.toDomain(dataContextJPARepository.save(DataContextEntityMapper.toEntity(dataContext)));
    }

    @Override
    public Optional<DataContext> findById(UUID id) {
        return dataContextJPARepository.findById(id).map(DataContextEntityMapper::toDomain);
    }
}
