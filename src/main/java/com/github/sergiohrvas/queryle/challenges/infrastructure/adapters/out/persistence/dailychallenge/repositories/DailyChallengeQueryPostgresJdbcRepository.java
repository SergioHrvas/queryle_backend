package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.out.persistence.dailychallenge.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import com.github.sergiohrvas.queryle.challenges.application.queries.getdailychallenges.DailyChallengeView;

@Repository 
public class DailyChallengeQueryPostgresJdbcRepository implements DailyChallengeQueryRepository {
    
    private final JdbcClient jdbcClient;

    public DailyChallengeQueryPostgresJdbcRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }
    
    @Override
    public List<DailyChallengeView> getDailyChallenges(UUID playerId, LocalDate dateFrom, LocalDate dateTo, String difficulty) {
        return jdbcClient.sql(
            """
            SELECT 
                dc.id,
                dc.formulation,
                dc.difficulty,
                dc.publication_date,
                g.status
            FROM daily_challenges dc
            LEFT JOIN games g ON dc.id = g.daily_challenge_id
               AND g.player_id = :playerId
            WHERE
                dc.publication_date BETWEEN :dateFrom AND :dateTo
                AND dc.difficulty = :difficulty
            ORDER BY dc.publication_date DESC
            """)
            .param("playerId", playerId)
            .param("difficulty", difficulty)
            .param("dateFrom", dateFrom)
            .param("dateTo", dateTo)
            .query((row, rowNum) -> 
                new DailyChallengeView(
                    UUID.fromString(row.getString("id")),
                    row.getString("formulation"),
                    row.getDate("publication_date").toLocalDate(),
                    row.getString("status")
                )
            )
            .list();
    }
}