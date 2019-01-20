/*
 * IntegerEncryptServiceTest.java
 *
 * @formatter:off
 *
 * @author Andrew
 *
 * Created: 17 Jan 2019
 *
 * @formatter:on
 */

package com.github.awferg.restex.service;

import com.gitbub.awferg.restex.TestConstants;

import com.github.awferg.restex.model.EncryptedUser;
import com.github.awferg.restex.model.User;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.slf4j.LoggerFactory;

public class UserEncryptServiceTest {

    private EncryptDecrypt<User, EncryptedUser> encryptService;

    @Before
    public void setUp() {

        final EncryptService stringEncryptService =
                new EncryptService(LoggerFactory.getLogger(EncryptService.class));
        final IntegerEncryptService integerEncryptService =
                new IntegerEncryptService(stringEncryptService);

        this.encryptService = new UserEncryptService(stringEncryptService, integerEncryptService);
    }

    @Test
    public void testEncryptDecrypt() {

        final EncryptedUser encrypted = this.encryptService.encrypt(TestConstants.USER);

        Assert.assertEquals(TestConstants.USER, this.encryptService.decrypt(encrypted));
    }

    @Test
    public void testEncryptDecryptNull() {

        Assert.assertNull(this.encryptService.decrypt(null));
    }

    @Test
    public void testEncryptDecryptNullValues() {

        final User user = new User(TestConstants.BIG_NAME_FIRSTNAME,
                TestConstants.BIG_NAME_SURNAME, null, 0);

        final EncryptedUser encrypted = this.encryptService.encrypt(user);

        Assert.assertEquals(user, this.encryptService.decrypt(encrypted));
    }
}
