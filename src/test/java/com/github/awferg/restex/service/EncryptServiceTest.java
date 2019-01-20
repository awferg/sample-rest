/*
 * EncryptServiceTest.java
 *
 * @formatter:off
 *
 * @author Andrew
 *
 * Created: 16 Jan 2019
 *
 * @formatter:on
 */

package com.github.awferg.restex.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.slf4j.LoggerFactory;

public class EncryptServiceTest {

    private EncryptDecrypt<String, String> encryptService;

    @Before
    public void setUp() {

        encryptService = new EncryptService(LoggerFactory.getLogger(EncryptService.class));
    }

    @Test
    public void testEncryptDecrypt() {

        final String testString = "I'm going to be encrypted";

        final String encrypted = encryptService.encrypt(testString);

        Assert.assertEquals(testString, encryptService.decrypt(encrypted));
    }

    @Test
    public void testEncryptDecryptChina() {

        final String testString = "我要加密了";

        final String encrypted = encryptService.encrypt(testString);

        Assert.assertEquals(testString, encryptService.decrypt(encrypted));
    }
}
