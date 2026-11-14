package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.out.persistence.player;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.github.sergiohrvas.queryle.challenges.domain.models.Player;
import com.github.sergiohrvas.queryle.challenges.domain.repositories.PlayerRepository;
import com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.out.persistence.player.mappers.PlayerEntityMapper;
import com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.out.persistence.player.repositories.PlayerJPARepository;

@Component
public class PlayerRepositoryAdapter implements PlayerRepository {
    private final PlayerJPARepository playerJPARepository;

    public PlayerRepositoryAdapter(PlayerJPARepository playerJPARepository) {
        this.playerJPARepository = playerJPARepository;
    }

    @Override
    public void save(Player player) {
        playerJPARepository.save(PlayerEntityMapper.toEntity(player));
    }

    @Override
    public Optional<Player> findByIdentitySubject(String identitySubject) {
        return playerJPARepository.findByIdentitySubject(identitySubject).map(PlayerEntityMapper::toDomain);
    }

}
