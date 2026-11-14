package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.out.persistence.player.mappers;

import com.github.sergiohrvas.queryle.challenges.domain.models.Player;
import com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.out.persistence.player.entities.PlayerEntity;

public class PlayerEntityMapper {
    public static PlayerEntity toEntity(Player player) {
        return new PlayerEntity(player.getId(), player.getUsername(), player.getEmail(), player.getIdentitySubject(), player.getIdentityIssuer());
    }
    
    public static Player toDomain(PlayerEntity playerEntity) {
        return new Player(playerEntity.getId(), playerEntity.getUsername(), playerEntity.getEmail(), playerEntity.getIdentitySubject(), playerEntity.getIdentityIssuer());
    }
}
