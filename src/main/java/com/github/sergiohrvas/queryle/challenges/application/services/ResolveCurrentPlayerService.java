package com.github.sergiohrvas.queryle.challenges.application.services;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.github.sergiohrvas.queryle.challenges.domain.models.Player;
import com.github.sergiohrvas.queryle.challenges.domain.repositories.PlayerRepository;

@Service
public class ResolveCurrentPlayerService {

    private final PlayerRepository playerRepository;

    public ResolveCurrentPlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public UUID resolve(String identitySubject, String username, String email) {
        return playerRepository.findByIdentitySubject(identitySubject)
            .map(Player::getId)
            .orElseGet(() -> {
                Player player = new Player(
                    UUID.randomUUID(),
                    username,
                    email,
                    identitySubject
                );

                playerRepository.save(player);
                return player.getId();
            });
    }
}