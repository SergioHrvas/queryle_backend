package com.github.sergiohrvas.queryle.challenges.domain.models;

import java.util.Objects;
import java.util.UUID;

import com.github.sergiohrvas.queryle.challenges.domain.exceptions.InvalidPlayerException;

public class Player {
    private final UUID id;
    private final String username;
    private final String email;
    private final String identitySubject;

    public Player(UUID id, String username, String email, String identitySubject) {
        validateState(id, username, email, identitySubject);
        this.id = id;
        this.username = username;
        this.email = email;
        this.identitySubject = identitySubject;
    }

    private void validateState(UUID id, String username, String email, String identitySubject) {
        if (Objects.isNull(id)) {
            throw new InvalidPlayerException("Player ID cannot be null");
        }
        if (Objects.isNull(username) || username.isBlank()) {
            throw new InvalidPlayerException("Username cannot be null or blank");
        }
        if (Objects.isNull(email) || email.isBlank()) {
            throw new InvalidPlayerException("Email cannot be null or blank");
        }
        if (Objects.isNull(identitySubject) || identitySubject.isBlank()) {
            throw new InvalidPlayerException("Identity subject cannot be null or blank");
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new InvalidPlayerException("Email format is invalid");
        }
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getIdentitySubject() {
        return identitySubject;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", identitySubject='" + identitySubject + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (Objects.isNull(o) || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return id.equals(player.id) && username.equals(player.username) 
            && email.equals(player.email) && identitySubject.equals(player.identitySubject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, identitySubject);
    }
}
