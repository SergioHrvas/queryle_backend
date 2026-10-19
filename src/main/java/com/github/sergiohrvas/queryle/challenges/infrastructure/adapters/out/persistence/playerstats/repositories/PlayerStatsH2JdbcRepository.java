package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.out.persistence.playerstats.repositories;

import java.util.UUID;

import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import com.github.sergiohrvas.queryle.challenges.application.queries.getplayerstats.PlayerStatsReadModel;

@Primary
@Repository
public class PlayerStatsH2JdbcRepository implements PlayerStatsRepository {
    private final JdbcClient jdbcClient;

    public PlayerStatsH2JdbcRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public PlayerStatsReadModel getStats(UUID playerId) {
        return jdbcClient.sql(
                """
                        WITH completed_games AS (
                            SELECT
                                games.id,
                                games.daily_challenge_id,
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
                            GROUP BY
                                games.id,
                                games.daily_challenge_id,
                                games.status,
                                daily_challenges.publication_date
                        ),

                        game_stats AS (
                            SELECT
                                COALESCE(
                                    SUM(CASE WHEN status = 'COMPLETED_WON' THEN 1 ELSE 0 END),
                                    0
                                ) AS wins,

                                COALESCE(
                                    SUM(CASE WHEN status = 'COMPLETED_FAILED' THEN 1 ELSE 0 END),
                                    0
                                ) AS losses,

                                COUNT(*) AS total_games

                            FROM completed_games
                        ),

                        challenge_results AS (
                            SELECT
                                dc.id AS daily_challenge_id,
                                dc.publication_date,

                                CASE
                                    WHEN cg.status = 'COMPLETED_WON'
                                        AND cg.completed_date = dc.publication_date
                                    THEN 'WON'

                                    WHEN cg.status = 'COMPLETED_FAILED'
                                        OR (
                                            cg.status = 'COMPLETED_WON'
                                            AND cg.completed_date <> dc.publication_date
                                        )
                                    THEN 'FAILED'

                                    ELSE 'NOT_PLAYED'
                                END AS result

                            FROM daily_challenges dc

                            LEFT JOIN completed_games cg
                                ON cg.daily_challenge_id = dc.id

                            WHERE dc.publication_date <= CURRENT_DATE
                        ),

                        streakable_challenges AS (
                            SELECT
                                daily_challenge_id,
                                publication_date,
                                result
                            FROM challenge_results

                            WHERE publication_date < CURRENT_DATE
                               OR result <> 'NOT_PLAYED'
                        ),

                        ordered_challenges AS (
                            SELECT
                                daily_challenge_id,
                                publication_date,
                                result,

                                ROW_NUMBER() OVER (
                                    ORDER BY publication_date
                                )
                                -
                                ROW_NUMBER() OVER (
                                    PARTITION BY
                                        CASE WHEN result = 'WON' THEN 1 ELSE 0 END
                                    ORDER BY publication_date
                                ) AS streak_group

                            FROM streakable_challenges
                        ),

                        winning_streaks AS (
                            SELECT
                                streak_group,
                                COUNT(*) AS streak_length,
                                MAX(publication_date) AS end_date

                            FROM ordered_challenges

                            WHERE result = 'WON'

                            GROUP BY streak_group
                        ),

                        streak_stats AS (
                            SELECT
                                COALESCE(
                                    MAX(streak_length),
                                    0
                                ) AS longest_streak,

                                COALESCE(
                                    MAX(
                                        CASE
                                            WHEN end_date = (
                                                SELECT MAX(publication_date)
                                                FROM streakable_challenges
                                            )
                                            THEN streak_length
                                            ELSE 0
                                        END
                                    ),
                                    0
                                ) AS current_streak

                            FROM winning_streaks
                        )

                        SELECT
                            game_stats.wins,
                            game_stats.losses,
                            game_stats.total_games,
                            streak_stats.current_streak,
                            streak_stats.longest_streak

                        FROM game_stats
                        CROSS JOIN streak_stats
                        """)
                .param("playerId", playerId)
                .query((row, rowNum) -> new PlayerStatsReadModel(
                        playerId,
                        row.getInt("total_games"),
                        row.getInt("wins"),
                        row.getInt("losses"),
                        row.getInt("current_streak"),
                        row.getInt("longest_streak")))
                .single();
    }
}
