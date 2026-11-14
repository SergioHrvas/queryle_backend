package com.github.sergiohrvas.queryle.challenges.application.services;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.github.sergiohrvas.queryle.challenges.application.exceptions.PlayerNotRegisteredException;
import com.github.sergiohrvas.queryle.challenges.domain.models.ExternalIdentity;
import com.github.sergiohrvas.queryle.challenges.domain.models.Player;
import com.github.sergiohrvas.queryle.challenges.domain.repositories.PlayerRepository;

@Service
public class ResolveCurrentPlayerService {

    private final PlayerRepository playerRepository;

    public ResolveCurrentPlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public UUID resolve(ExternalIdentity identity) {
        return playerRepository.findByIdentity(identity)
            .map(Player::getId)
            .orElseThrow(() -> new PlayerNotRegisteredException(identity));
    }
}