package com.github.sergiohrvas.queryle.challenges.infrastructure.adapters.out.sql;

import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Component;

@Component
public class H2SqlScriptExecutor {

    public void execute(Statement statement, String sql)
            throws SQLException {

        for (String part : sql.split(";")) {
            String statementSql = part.trim();

            if (!statementSql.isEmpty()) {
                statement.execute(statementSql);
            }
        }
    }
}