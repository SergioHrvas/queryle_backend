package com.github.sergiohrvas.queryle.challenges.domain.repositories;

import java.util.Optional;

import com.github.sergiohrvas.queryle.challenges.domain.models.Player;
import com.github.sergiohrvas.queryle.challenges.domain.models.ExternalIdentity;

public interface PlayerRepository {
    Optional<Player> findByIdentity(ExternalIdentity identity);
    void save(Player player);
}
