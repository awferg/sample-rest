/*
 * User.java
 *
 * @formatter:off
 *
 * @author Andrew
 *
 * Created: 16 Jan 2019
 *
 * @formatter:on
 */

package com.github.awferg.restex.database;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ExistsMapper implements RowMapper<Boolean> {

    @Override
    public Boolean map(final ResultSet rs, final StatementContext ctx) throws SQLException {

        if (rs.next()) {
            return rs.getInt(1) > 0;
        }

        return false;
    }
}
