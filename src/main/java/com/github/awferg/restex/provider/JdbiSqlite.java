/*
 * JdbiSqlite.java
 *
 * @formatter:off
 *
 *
 * @author Andrew
 *
 * Created: 19 Jan 2019
 *
 * @formatter:on
 */

package com.github.awferg.restex.provider;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlite3.SQLitePlugin;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import org.slf4j.Logger;

import java.io.File;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

@ApplicationScoped
public class JdbiSqlite {

    private final Logger logger;
    private final String databaseLocation =
            System.getProperty("com.github.awferg.restex.provider.JdbiSqlite.databaseLocation");

    @Inject
    public JdbiSqlite(final Logger logger) {

        this.logger = logger;
    }

    /**
     * Produces a jdbi instance for sqlite.
     *
     * @return Jbbi instance for sqlite
     */
    @Produces
    @Dependent
    @Default
    public Jdbi getJdbi() {

        return Jdbi.create("jdbc:sqlite:" + this.getDatabaseLocation())
                .installPlugin(new SQLitePlugin()).installPlugin(new SqlObjectPlugin());
    }

    /**
     * Validates system property if set and uses it for database location.
     *
     * @return The database location.
     */
    private String getDatabaseLocation() {

        String location = null;

        if (this.databaseLocation != null) {

            final File dbFile = new File(this.databaseLocation);
            final File dbDirectory = dbFile.getParentFile();

            if (dbDirectory != null && !dbDirectory.isDirectory()) {
                // not going to create directories if they don't exist
                this.logger.info("No such directory {}", dbFile.getParent());
            } else {
                location = this.databaseLocation;
            }
        }

        if (location == null) {
            location = "database";
        }
        this.logger.info("Database will be created at {}", location);

        return location;
    }
}
