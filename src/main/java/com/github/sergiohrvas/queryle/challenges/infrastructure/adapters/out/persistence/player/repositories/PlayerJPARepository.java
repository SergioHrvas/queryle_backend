package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.out.persistence.player.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.out.persistence.player.entities.PlayerEntity;

@Repository
public interface PlayerJPARepository extends JpaRepository<PlayerEntity, UUID> {
    Optional<PlayerEntity> findByIdentityIssuerAndIdentitySubject(String identityIssuer, String identitySubject);

}