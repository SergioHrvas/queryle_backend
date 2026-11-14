package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.out.persistence.player.entities;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity 
@Table (name = "players")
public class PlayerEntity {

    @Id
    private UUID id;

    @Column (name = "identity_subject", nullable = false, unique = true)
    private String identitySubject;

    @Column (nullable = false, unique = true)
    private String username;

    @Column (nullable = false, unique = true)
    private String email;

    @Column (name = "created_at", nullable = false)
    private Instant createdAt;

    public PlayerEntity(UUID id, String username, String email, String identitySubject) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.identitySubject = identitySubject;
    }

    protected PlayerEntity() {}

    @PrePersist
    protected void onCreate() {
        this.createdAt = Instant.now();
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

    public Instant getCreatedAt() {
        return createdAt;
    }
}