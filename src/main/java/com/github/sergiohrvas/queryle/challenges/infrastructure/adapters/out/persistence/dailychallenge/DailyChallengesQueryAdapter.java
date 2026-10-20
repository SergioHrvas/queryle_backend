package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.out.persistence.dailychallenge;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.github.sergiohrvas.queryle.challenges.application.ports.DailyChallengesQueryPort;
import com.github.sergiohrvas.queryle.challenges.application.queries.getdailychallenges.DailyChallengeView;
import com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.out.persistence.dailychallenge.repositories.DailyChallengeQueryRepository;

@Component 
public class DailyChallengesQueryAdapter implements DailyChallengesQueryPort {

    private final DailyChallengeQueryRepository repository;


    public DailyChallengesQueryAdapter(DailyChallengeQueryRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<DailyChallengeView> getDailyChallenges(UUID playerId, LocalDate dateFrom, LocalDate dateTo, String difficulty) {
        return repository.getDailyChallenges(playerId, dateFrom, dateTo, difficulty);
    }
}
