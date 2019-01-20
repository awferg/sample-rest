/*
 * UserDaoImpl.java
 *
 * @formatter:off
 *
 * @author Andrew
 *
 * Created: 16 Jan 2018
 *
 * @formatter:on
 */

package com.github.awferg.restex.database;

import com.github.awferg.restex.model.EncryptedUser;

import org.jdbi.v3.core.Jdbi;

import org.slf4j.Logger;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class UserDaoImpl implements UserDao {

    private final UserDao userDao;
    private final Jdbi jdbi;
    private final Logger logger;

    public UserDaoImpl() {

        // for proxying
        this(null, null);
    }

    @Inject
    public UserDaoImpl(final Jdbi jdbi, final Logger logger) {

        this.jdbi = jdbi;
        this.userDao = this.jdbi == null ? null : jdbi.onDemand(UserDao.class);
        this.logger = logger;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.github.awferg.restex.database.UserDao#count()
     */
    @Override
    public int count() {

        return this.userDao.count();
    }

    /*
     * (non-Javadoc)
     *
     * @see com.github.awferg.restex.database.UserDao#createTable()
     */
    @Override
    public void createTable() {

        this.logger.debug("Creating user table");
        this.userDao.createTable();
        this.logger.debug("Created user table");
    }

    /*
     * (non-Javadoc)
     *
     * @see com.github.awferg.restex.database.UserDao#dataExists(com.github.awferg.restex.model.EncryptedUser)
     */
    @Override
    public boolean dataExists(final EncryptedUser record) {

        return this.userDao.dataExists(record);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.github.awferg.restex.database.UserDao#delete(int)
     */
    @Override
    public void delete(final int id) {

        this.userDao.delete(id);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.github.awferg.restex.database.UserDao#dropTable()
     */
    @Override
    public void dropTable() {

        this.userDao.dropTable();
    }

    /*
     * (non-Javadoc)
     *
     * @see com.github.awferg.restex.database.UserDao#exists(int)
     */
    @Override
    public boolean exists(final int id) {

        return this.userDao.exists(id);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.github.awferg.restex.database.UserDao#find(int)
     */
    @Override
    public EncryptedUser find(final int id) {

        return this.userDao.find(id);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.github.awferg.restex.database.UserDao#findAll()
     */
    @Override
    public List<EncryptedUser> findAll() {

        return this.userDao.findAll();
    }

    @PostConstruct
    public void initTable() {

        this.createTable();
    }

    /*
     * (non-Javadoc)
     *
     * @see com.github.awferg.restex.database.UserDao#insert(com.github.awferg.restex.model.EncryptedUser)
     */
    @Override
    public int insert(final EncryptedUser user) {

        return this.userDao.insert(user);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.github.awferg.restex.database.UserDao#update(com.github.awferg.restex.model.EncryptedUser)
     */
    @Override
    public void update(final EncryptedUser record) {

        this.userDao.update(record);
    }
}
