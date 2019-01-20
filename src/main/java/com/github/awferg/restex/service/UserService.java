/*
 * UserService.java
 *
 * @formatter:off
 *
 * @author Andrew
 *
 * Created: 15 Jan 2019
 *
 * @formatter:on
 */

package com.github.awferg.restex.service;

import com.github.awferg.restex.database.UserDao;
import com.github.awferg.restex.model.EncryptedUser;
import com.github.awferg.restex.model.User;

import org.slf4j.Logger;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.validation.Valid;

@Dependent
public class UserService {

    private final UserDao userDao;
    private final Logger logger;
    private final UserEncryptService userEncryptService;

    @Inject
    public UserService(final UserDao userDao, final UserEncryptService userEncryptService,
            final Logger logger) {

        this.userDao = userDao;
        this.userEncryptService = userEncryptService;
        this.logger = logger;
    }

    /**
     * Add a user.
     *
     * @param user to be added
     * @return id of added user
     */
    public int add(@Valid final User user) {

        Objects.requireNonNull(user, "User must be supplied");

        final EncryptedUser encryptedUser = this.userEncryptService.encrypt(user);

        // check if same data exists, has to be all the data as people have the same names
        if (this.userDao.dataExists(encryptedUser)) {
            /*
             * TODO decide what to do if duplicate data exists, go with just logging it for now and inserting it Option is to
             * throw custom error to be trapped and send back 409 ? But the client may have not got first reply back and be
             * retrying. To distinguish between retries and new data you would also send a token which wouldn't change on
             * retry. Token needs to be client specific and have some incremental portion. You can then have 'small' cache of
             * ids to tokens where we can check if it is a retry, tokens with ids cleared out when the incremental part moves
             * on.
             */
            this.logger.warn("A request has been received to add data {} that is a duplicate",
                    user);
        }

        return this.userDao.insert(encryptedUser);
    }

    /**
     * Find user by id.
     *
     * @param id of user
     * @return User details
     */
    public User find(final int id) {

        return this.userEncryptService.decrypt(this.userDao.find(id));
    }

    /**
     * Get all users.
     *
     * @return List of Users
     */
    public List<User> getAll() {

        return this.userDao.findAll().stream().map(this.userEncryptService::decrypt)
                .collect(Collectors.toList());
    }

    /**
     * Get all users as they are in database.
     *
     * @return List of Encrypted Users
     */
    public List<EncryptedUser> getAllEncrypted() {

        return this.userDao.findAll();
    }

    /**
     * Deletes user by id.
     *
     * @param id of user
     */
    public void delete(int id) {

        this.userDao.delete(id);
    }
}
