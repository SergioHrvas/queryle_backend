package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.out.persistence.datacontext;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.github.sergiohrvas.queryle.challenges.domain.models.DataContext;
import com.github.sergiohrvas.queryle.challenges.domain.repositories.DataContextRepository;
import com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.out.persistence.datacontext.mappers.DataContextEntityMapper;
import com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.out.persistence.datacontext.repositories.DataContextJPARepository;

@Component
public class DataContextRepositoryAdapter implements DataContextRepository {
    
    private final DataContextJPARepository dataContextJPARepository;

    public DataContextRepositoryAdapter(DataContextJPARepository dataContextJPARepository) {
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
