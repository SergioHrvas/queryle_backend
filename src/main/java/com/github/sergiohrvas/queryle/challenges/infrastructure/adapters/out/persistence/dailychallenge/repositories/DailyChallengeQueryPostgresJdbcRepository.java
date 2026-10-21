package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.out.persistence.dailychallenge.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import com.github.sergiohrvas.queryle.challenges.application.exceptions.DailyChallengeNotFoundException;
import com.github.sergiohrvas.queryle.challenges.application.queries.getdailychallenge.DailyChallengeAttemptView;
import com.github.sergiohrvas.queryle.challenges.application.queries.getdailychallenge.DailyChallengeDetailView;
import com.github.sergiohrvas.queryle.challenges.application.queries.getdailychallenge.DailyChallengeGameView;
import com.github.sergiohrvas.queryle.challenges.application.queries.getdailychallenges.DailyChallengeSummaryView;
import com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.out.persistence.dailychallenge.repositories.dto.DailyChallengeDetailRow;

@Repository
public class DailyChallengeQueryPostgresJdbcRepository implements DailyChallengeQueryRepository {

    private final JdbcClient jdbcClient;

    public DailyChallengeQueryPostgresJdbcRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public List<DailyChallengeSummaryView> getDailyChallenges(UUID playerId, LocalDate dateFrom, LocalDate dateTo,
            String difficulty) {
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
                .query((row, rowNum) -> new DailyChallengeSummaryView(
                        row.getObject("id", UUID.class),
                        row.getString("formulation"),
                        row.getDate("publication_date").toLocalDate(),
                        row.getString("status")))
                .list();
    }

    @Override
    public DailyChallengeDetailView getDailyChallenge(UUID playerId, UUID challengeId) {
        List<DailyChallengeDetailRow> rows = jdbcClient.sql(
                """
                        SELECT
                            dc.id,
                            dc.formulation,
                            dc.difficulty,
                            dc.publication_date,
                            ctx.name,
                            ctx.description,
                            ctx.schema,
                            ctx.public_dataset,

                            g.id AS game_id,
                            g.status AS game_status,

                            a.id AS attempt_id,
                            a.query AS attempt_query,
                            a.submitted_at,
                            a.validation_passed,
                            a.columns_names_match_level,
                            a.columns_count_match_level,
                            a.rows_count_match_level,
                            a.rows_data_match_level,
                            a.rows_order_match_level
                        FROM daily_challenges dc
                        JOIN data_contexts ctx
                            ON dc.data_context_id = ctx.id
                        LEFT JOIN games g
                            ON dc.id = g.daily_challenge_id
                           AND g.player_id = :playerId
                        LEFT JOIN attempts a
                            ON a.game_id = g.id
                        WHERE dc.id = :challengeId
                        ORDER BY a.submitted_at ASC
                        """)
                .param("playerId", playerId)
                .param("challengeId", challengeId)
                .query((row, rowNum) -> new DailyChallengeDetailRow(
                        row.getObject("id", UUID.class),
                        row.getString("formulation"),
                        row.getString("difficulty"),
                        row.getDate("publication_date").toLocalDate(),
                        row.getString("name"),
                        row.getString("description"),
                        row.getString("schema"),
                        row.getString("public_dataset"),
                        row.getObject("game_id", UUID.class),
                        row.getString("game_status"),
                        row.getObject("attempt_id", UUID.class),
                        row.getString("attempt_query"),
                        row.getTimestamp("submitted_at"),
                        row.getObject("validation_passed", Boolean.class),
                        row.getString("columns_names_match_level"),
                        row.getString("columns_count_match_level"),
                        row.getString("rows_count_match_level"),
                        row.getString("rows_data_match_level"),
                        row.getString("rows_order_match_level")))
                .list();

        if (rows.isEmpty()) {
            throw new DailyChallengeNotFoundException(challengeId);
        }

        DailyChallengeDetailRow first = rows.get(0);

        List<DailyChallengeAttemptView> attempts = rows.stream()
                .filter(row -> Objects.nonNull(row.attemptId()))
                .map(row -> new DailyChallengeAttemptView(
                        row.attemptId(),
                        row.attemptQuery(),
                        row.submittedAt().toLocalDateTime(),
                        row.validationPassed(),
                        row.columnsNamesMatchLevel(),
                        row.columnsCountMatchLevel(),
                        row.rowsCountMatchLevel(),
                        row.rowsDataMatchLevel(),
                        row.rowsOrderMatchLevel()))
                .toList();

        DailyChallengeGameView game = Objects.nonNull(first.gameId()) ? new DailyChallengeGameView(
                first.gameStatus(),
                attempts)
                : null;

        return new DailyChallengeDetailView(
                first.id(),
                first.formulation(),
                first.difficulty(),
                first.publicationDate(),
                game,
                first.name(),
                first.description(),
                first.schema(),
                first.publicDataset());
    }
}