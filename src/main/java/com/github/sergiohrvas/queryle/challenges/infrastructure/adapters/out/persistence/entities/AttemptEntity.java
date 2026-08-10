package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.out.persistence.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "attempts")
public class AttemptEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id", nullable = false)
    private GameEntity game;

    @Column(name = "query", nullable = false, length = 2000)
    private String query;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;
    
    @Column(name = "columns_names_match_level", nullable = false)
    private String columnsNamesMatchLevel;

    @Column(name = "columns_count_match_level", nullable = false)
    private String columnsCountMatchLevel;

    @Column(name = "rows_count_match_level", nullable = false)
    private String rowsCountMatchLevel;

    @Column(name = "rows_data_match_level", nullable = false)
    private String rowsDataMatchLevel;

    @Column(name = "rows_order_match_level", nullable = false)
    private String rowsOrderMatchLevel;

    public AttemptEntity(GameEntity game, String query, LocalDateTime date, String columnsNamesMatchLevel, String columnsCountMatchLevel, String rowsCountMatchLevel, String rowsDataMatchLevel, String rowsOrderMatchLevel) {
        this.game = game;
        this.query = query;
        this.date = date;
        this.columnsNamesMatchLevel = columnsNamesMatchLevel;
        this.columnsCountMatchLevel = columnsCountMatchLevel;
        this.rowsCountMatchLevel = rowsCountMatchLevel;
        this.rowsDataMatchLevel = rowsDataMatchLevel;
        this.rowsOrderMatchLevel = rowsOrderMatchLevel;
    }

    protected AttemptEntity() {}

    public UUID getId() {
        return id;
    }

    public GameEntity getGame() {
        return game;
    }

    public String getQuery() {
        return query;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getColumnsNamesMatchLevel() {
        return columnsNamesMatchLevel;
    }
    
    public String getColumnsCountMatchLevel() {
        return columnsCountMatchLevel;
    }

    public String getRowsCountMatchLevel() {
        return rowsCountMatchLevel;
    }

    public String getRowsDataMatchLevel() {
        return rowsDataMatchLevel;
    }

    public String getRowsOrderMatchLevel() {
        return rowsOrderMatchLevel;
    }
}