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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.slf4j.LoggerFactory;

public class IntegerEncryptServiceTest {

    private EncryptDecrypt<Integer, String> encryptService;

    @Before
    public void setUp() {

        encryptService = new IntegerEncryptService(new EncryptService(LoggerFactory.getLogger(EncryptService.class)));
    }

    @Test
    public void testEncryptDecryptMax() {

        final Integer test = Integer.MAX_VALUE;

        final String encrypted = encryptService.encrypt(test);

        Assert.assertEquals(test, encryptService.decrypt(encrypted));
    }

    @Test
    public void testEncryptDecryptMin() {

        final Integer test = Integer.MIN_VALUE;

        final String encrypted = encryptService.encrypt(test);

        Assert.assertEquals(test, encryptService.decrypt(encrypted));
    }
}
