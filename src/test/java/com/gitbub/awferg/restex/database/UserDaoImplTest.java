package com.gitbub.awferg.restex.database;

import com.gitbub.awferg.restex.TestConstants;

import com.github.awferg.restex.database.UserDaoImpl;
import com.github.awferg.restex.model.EncryptedUser;
import com.github.awferg.restex.provider.JdbiSqlite;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.statement.UnableToExecuteStatementException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class UserDaoImplTest {

    private static UserDaoImpl userDao;

    @BeforeClass
    public static void init() {

        final Jdbi jdbi = new JdbiSqlite(LoggerFactory.getLogger(JdbiSqlite.class)).getJdbi();

        UserDaoImplTest.userDao = new UserDaoImpl(jdbi, LoggerFactory.getLogger(UserDaoImpl.class));
        UserDaoImplTest.userDao.dropTable();
        UserDaoImplTest.userDao.initTable();
    }

    @Test
    public void count() {

        UserDaoImplTest.userDao.insert(TestConstants.USER_ENCRYPTED);

        Assert.assertTrue(UserDaoImplTest.userDao.count() > 0);
    }

    @Test
    public void deleteUser() {

        final int id = UserDaoImplTest.userDao.insert(TestConstants.USER_ENCRYPTED);
        UserDaoImplTest.userDao.delete(id);

        Assert.assertFalse(UserDaoImplTest.userDao.exists(id));
    }

    @Test
    public void nullFieldUserOk() {

        final EncryptedUser nullFieldUser = new EncryptedUser("jjjjj", "ppppppppp", null, null);

        final int id = UserDaoImplTest.userDao.insert(nullFieldUser);
        nullFieldUser.setId(id);

        final EncryptedUser user = UserDaoImplTest.userDao.find(id);
        Assert.assertEquals(nullFieldUser, user);
    }

    @Test
    public void nullFieldUserNotOkFirstFeld() {

        final EncryptedUser nullFieldUser = new EncryptedUser(null, "ppppppppp", "x", "y");

        try {
            UserDaoImplTest.userDao.insert(nullFieldUser);
        } catch (final Exception exception) {
            if (exception instanceof UnableToExecuteStatementException && exception.getCause() instanceof SQLException) {

                int code = ((SQLException) exception.getCause()).getErrorCode();
                Assert.assertEquals("Unexpected SQLException code", code, 19);

                return;
            }

            Assert.fail("Unexpected exception "+ exception.toString());
        }

        Assert.fail("Null first name accepted");
    }

    @Test
    public void findUser() {

        final EncryptedUser userToFind = new EncryptedUser("jjjjj", "ppppppppp", "hhhhhhhhh", "");

        final int id = UserDaoImplTest.userDao.insert(userToFind);
        userToFind.setId(id);

        final EncryptedUser user = UserDaoImplTest.userDao.find(id);
        Assert.assertEquals(userToFind, user);
    }

    @Test
    public void insertUser() {

        final int id = UserDaoImplTest.userDao.insert(TestConstants.USER_ENCRYPTED);
        Assert.assertTrue(UserDaoImplTest.userDao.exists(id));
    }

    @Test
    public void noUser() {

        final EncryptedUser user = UserDaoImplTest.userDao.find(4566);
        Assert.assertNull(user);
    }

    @Test
    public void sameData() {

        UserDaoImplTest.userDao.insert(TestConstants.USER_ENCRYPTED);
        Assert.assertTrue(UserDaoImplTest.userDao.dataExists(TestConstants.USER_ENCRYPTED));
    }

    // TODO more tests, especially update and findAll
}
