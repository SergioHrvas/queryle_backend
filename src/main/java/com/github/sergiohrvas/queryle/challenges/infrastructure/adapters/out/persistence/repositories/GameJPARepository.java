package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.out.persistence.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.out.persistence.entities.GameEntity;

@Repository
public interface GameJPARepository extends JpaRepository<GameEntity, UUID> {
    Optional<GameEntity> findByPlayerIdAndDailyChallengeId(UUID playerId, UUID dailyChallengeId);
}