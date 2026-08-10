package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.out.persistence.repositories;

import org.springframework.stereotype.Repository;

import com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.out.persistence.entities.DailyChallengeEntity;

import java.util.UUID;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface DailyChallengeJPARepository extends JpaRepository<DailyChallengeEntity, UUID> {
    Optional<DailyChallengeEntity> findByPublicationDateAndDifficulty(LocalDate publicationDate, String difficulty);
}
