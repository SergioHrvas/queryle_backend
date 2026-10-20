package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.out.persistence.playerstats.repositories;

import java.util.UUID;

import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import com.github.sergiohrvas.queryle.challenges.application.queries.getplayerstats.PlayerStatsView;

@Primary
@Repository
public class PlayerStatsQueryPostgresJdbcRepository implements PlayerStatsQueryRepository {
    private final JdbcClient jdbcClient;

    public PlayerStatsQueryPostgresJdbcRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public PlayerStatsView getStats(UUID playerId) {
        return jdbcClient.sql(
            """
            WITH completed_games AS (
                SELECT
                    games.status,
                    daily_challenges.publication_date,
                    CAST(MAX(attempts.submitted_at) AS DATE) AS completed_date
                FROM games
                JOIN daily_challenges
                    ON daily_challenges.id = games.daily_challenge_id
                JOIN attempts
                    ON attempts.game_id = games.id
                WHERE games.player_id = :playerId
                    AND games.status IN ('COMPLETED_WON', 'COMPLETED_FAILED')
                GROUP BY games.id, games.status, daily_challenges.publication_date
            ),
            ordered_games AS (
                SELECT
                    status,
                    publication_date,
                    completed_date,
                    LAG(publication_date) OVER (ORDER BY publication_date DESC) AS previous_date
                FROM completed_games
            ),
            streak_groups AS (
                SELECT
                    status,
                    publication_date,
                    completed_date,
                    SUM(
                        CASE
                            WHEN status <> 'COMPLETED_WON'
                                OR completed_date <> publication_date
                                OR (
                                    previous_date IS NOT NULL
                                    AND publication_date <> previous_date - INTERVAL '1 day'
                                )
                            THEN 1
                            ELSE 0
                        END
                    ) OVER (
                        ORDER BY publication_date DESC
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
                COALESCE(MAX(publication_date), CURRENT_DATE) AS last_challenge_date
            FROM streak_groups
            LEFT JOIN streak_lengths USING (streak_group)
            """)
            .param("playerId", playerId)
            .query((row, rowNum) -> new PlayerStatsView(
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
