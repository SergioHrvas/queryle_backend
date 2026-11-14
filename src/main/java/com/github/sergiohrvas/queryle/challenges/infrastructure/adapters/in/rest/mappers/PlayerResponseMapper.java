package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.mappers;

import com.github.sergiohrvas.queryle.challenges.domain.models.Player;
import com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.in.rest.dtos.response.PlayerResponse;

public class PlayerResponseMapper {
    public static PlayerResponse toPlayerResponse(Player player) {
        return new PlayerResponse(player.getId(), player.getUsername(), player.getEmail(), player.getIdentitySubject());
    }
}
