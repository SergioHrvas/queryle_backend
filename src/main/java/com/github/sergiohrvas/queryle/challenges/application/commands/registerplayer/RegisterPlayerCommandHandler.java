package com.github.sergiohrvas.queryle.challenges.application.commands.registerplayer;

import java.util.UUID;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.github.sergiohrvas.queryle.challenges.domain.models.Player;
import com.github.sergiohrvas.queryle.challenges.domain.repositories.PlayerRepository;

@Service
public class RegisterPlayerCommandHandler {
    private final PlayerRepository playerRepository;

    public RegisterPlayerCommandHandler(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public void handle(RegisterPlayerCommand command) {
        if (playerRepository.findByIdentity(command.identity()).isEmpty()) {
            Player player = new Player(
                UUID.randomUUID(),
                command.username(),
                command.email(),
                command.identity().subject(),
                command.identity().issuer()
            );
            try {
                playerRepository.save(player);
            } catch (DataIntegrityViolationException exception) {
                if (playerRepository.findByIdentity(command.identity()).isEmpty()) {
                    throw exception;
                }
            }
        }
    }
}
