/*
 * UserDao.java
 *
 * @formatter:off
 *
 * @author Andrew
 *
 * Created: 15 Jan 2019
 *
 * @formatter:on
 */

package com.github.awferg.restex.database;

import com.github.awferg.restex.model.EncryptedUser;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

public interface UserDao extends CrudDao<EncryptedUser>, DbTable {

    /*
     * (non-Javadoc)
     *
     * @see com.github.awferg.restex.database.CrudDao#count()
     */
    @Override
    @SqlQuery("SELECT count(*) FROM user")
    int count();

    /*
     * (non-Javadoc)
     *
     * @see com.github.awferg.restex.database.DbTable#createTable()
     */
    @Override
    @SqlUpdate("CREATE TABLE IF NOT EXISTS user (id INTEGER PRIMARY KEY, firstName VARCHAR(255) NOT NULL, "
            + "lastName VARCHAR(255) NOT NULL, email VARCHAR(255), age VARCHAR(255))")
    void createTable();

    // TODO using select 1 with limit doesn't seem to map correctly, count isn't as efficient
    @SqlQuery("SELECT count(*) FROM user WHERE firstName = :u.firstName AND lastName = :u.lastName AND email = :u.email AND age = :u.age")
    @RegisterRowMapper(ExistsMapper.class)
    boolean dataExists(@BindBean("u") EncryptedUser record);

    /*
     * (non-Javadoc)
     *
     * @see com.github.awferg.restex.database.CrudDao#delete(int)
     */
    @Override
    @SqlUpdate("DELETE FROM user WHERE id = :id")
    void delete(@Bind("id") int id);

    /*
     * (non-Javadoc)
     *
     * @see com.github.awferg.restex.database.DbTable#dropTable()
     */
    @Override
    @SqlUpdate("DROP TABLE IF EXISTS user")
    void dropTable();

    /*
     * (non-Javadoc)
     *
     * @see com.github.awferg.restex.database.CrudDao#exists(int)
     */
    @Override
    @SqlQuery("SELECT count(*) FROM user WHERE id = :id")
    @RegisterRowMapper(ExistsMapper.class)
    boolean exists(@Bind("id") int id);

    /*
     * (non-Javadoc)
     *
     * @see com.github.awferg.restex.database.CrudDao#find(int)
     */
    @Override
    @SqlQuery("SELECT id, firstName, lastName, email, age FROM user WHERE id = :id")
    @RegisterBeanMapper(value = EncryptedUser.class)
    EncryptedUser find(@Bind("id") int id);

    /*
     * (non-Javadoc)
     *
     * @see com.github.awferg.restex.database.CrudDao#findAll()
     */
    @Override
    @SqlQuery("SELECT id, firstName, lastName, email, age FROM user ORDER BY lastName")
    @RegisterBeanMapper(value = EncryptedUser.class)
    List<EncryptedUser> findAll();

    /*
     * (non-Javadoc)
     *
     * @see com.github.awferg.restex.database.CrudDao#insert(java.lang.Object)
     */
    @Override
    @SqlUpdate("INSERT INTO user(firstName, lastName, email, age) VALUES (:u.firstName, :u.lastName, :u.email, :u.age)")
    @GetGeneratedKeys
    int insert(@BindBean("u") EncryptedUser user);

    /*
     * (non-Javadoc)
     *
     * @see com.github.awferg.restex.database.CrudDao#update(java.lang.Object)
     */
    @Override
    @SqlUpdate("UPDATE user SET firstName = :u.firstName, lastName = :u.lastName, email = :u.email, age = :u.age WHERE id = :vid")
    void update(@BindBean("u") EncryptedUser record);
}
