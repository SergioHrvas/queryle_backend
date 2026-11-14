package com.github.sergiohrvas.queryle.challenges.domain.repositories;

import java.util.Optional;

import com.github.sergiohrvas.queryle.challenges.domain.models.Player;

public interface PlayerRepository {
    void save(Player player);
    Optional<Player> findByIdentitySubject(String identitySubject);
}
