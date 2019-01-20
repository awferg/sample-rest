/*
 * UserServiceTest.java
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

package com.github.awferg.restex.service;

import com.github.awferg.restex.database.UserDao;
import com.github.awferg.restex.database.UserDaoImpl;
import com.github.awferg.restex.model.User;
import com.github.awferg.restex.provider.JdbiSqlite;

import org.jdbi.v3.core.Jdbi;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.slf4j.LoggerFactory;

import java.util.Collections;

public class UserServiceTest {

    private final static String BIG_NAME_FIRSTNAME = String.join("", Collections.nCopies(50, "F"));

    private final static String BIG_NAME_SURNAME = String.join("", Collections.nCopies(50, "S"));
    private final static String BIG_EMAIL = String.join("", Collections.nCopies(30, "E"));
    private final static int BIG_AGE = 150;
    final static User USER = new User(UserServiceTest.BIG_NAME_FIRSTNAME,
            UserServiceTest.BIG_NAME_SURNAME, UserServiceTest.BIG_EMAIL, UserServiceTest.BIG_AGE);

    private UserService userService;

    @Test
    public void addUser() {

        final int id = this.userService.add(UserServiceTest.USER);

        Assert.assertTrue("Unexpected id returned", id > 0);
    }

    @Test
    public void deleteUser() {

        final int id = this.userService.add(UserServiceTest.USER);

        this.userService.delete(id);

        Assert.assertNull("User hasn't been deleted", this.userService.find(id));
    }

    @Test
    public void findAll() {

        this.userService.add(UserServiceTest.USER);

        Assert.assertFalse(this.userService.getAll().isEmpty());
    }

    @Test
    public void findUser() {

        final User user =
                new User(UserServiceTest.BIG_NAME_FIRSTNAME, UserServiceTest.BIG_NAME_SURNAME,
                        UserServiceTest.BIG_EMAIL, UserServiceTest.BIG_AGE);

        final int id = this.userService.add(user);
        user.setId(id);

        final User foundUser = this.userService.find(id);

        Assert.assertEquals("User found isn't correct", user, foundUser);
    }

    @Before
    public void setUp() {

        final EncryptService stringEncryptService =
                new EncryptService(LoggerFactory.getLogger(EncryptService.class));
        final IntegerEncryptService integerEncryptService =
                new IntegerEncryptService(stringEncryptService);

        final UserEncryptService encryptService =
                new UserEncryptService(stringEncryptService, integerEncryptService);

        final Jdbi jdbi = new JdbiSqlite(LoggerFactory.getLogger(JdbiSqlite.class)).getJdbi();

        final UserDao userDao = new UserDaoImpl(jdbi, LoggerFactory.getLogger(UserDaoImpl.class));
        userDao.dropTable();
        userDao.createTable();

        this.userService = new UserService(userDao, encryptService,
                LoggerFactory.getLogger(UserService.class));
    }
}
