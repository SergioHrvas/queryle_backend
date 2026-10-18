package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.out.persistence.repositories;

import java.util.UUID;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import com.github.sergiohrvas.queryle.challenges.application.dtos.PlayerStatsReadModel;

@Repository
public class PostgresJdbcPlayerStatsQueryRepository {
    private final JdbcClient jdbcClient;

    public PostgresJdbcPlayerStatsQueryRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public PlayerStatsReadModel getStats(UUID playerId) {
        return jdbcClient.sql(
            """
            WITH completed_games AS (
                SELECT
                    games.status,
                    daily_challenges.sequence
                FROM games
                JOIN daily_challenges
                    ON daily_challenges.id = games.daily_challenge_id
                WHERE games.player_id = :playerId
                    AND games.status IN ('COMPLETED_WON', 'COMPLETED_FAILED')
            ),
            ordered_games AS (
                SELECT
                    status,
                    sequence,
                    LAG(sequence) OVER (ORDER BY sequence DESC) AS previous_sequence
                FROM completed_games
            ),
            streak_groups AS (
                SELECT
                    status,
                    sequence,
                    SUM(
                        CASE
                            WHEN status <> 'COMPLETED_WON'
                                OR (
                                    previous_sequence IS NOT NULL
                                    AND sequence <> previous_sequence - 1
                                )
                            THEN 1
                            ELSE 0
                        END
                    ) OVER (
                        ORDER BY sequence DESC
                        ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW
                    ) AS streak_group
                FROM ordered_games
            ),
            streak_lengths AS (
                SELECT
                    streak_group,
                    SUM(CASE WHEN status = 'COMPLETED_WON' THEN 1 ELSE 0 END) AS streak_length
                FROM streak_groups
                GROUP BY streak_group
            )
            SELECT
                COALESCE(SUM(CASE WHEN status = 'COMPLETED_WON' THEN 1 ELSE 0 END), 0) AS wins,
                COALESCE(SUM(CASE WHEN status = 'COMPLETED_FAILED' THEN 1 ELSE 0 END), 0) AS losses,
                COUNT(*) AS total_games,
                COALESCE(MAX(CASE WHEN streak_group = 0 THEN streak_length ELSE 0 END), 0) AS current_streak,
                COALESCE(MAX(streak_length), 0) AS longest_streak,
                COALESCE(MAX(sequence), 0) AS last_challenge_sequence
            FROM streak_groups
            LEFT JOIN streak_lengths USING (streak_group)
            """)
            .param("playerId", playerId)
            .query((row, rowNum) -> new PlayerStatsReadModel(
                playerId,
                row.getInt("total_games"),
                row.getInt("wins"),
                row.getInt("losses"),
                row.getInt("current_streak"),
                row.getInt("longest_streak")
            ))
            .single();
    }
}
