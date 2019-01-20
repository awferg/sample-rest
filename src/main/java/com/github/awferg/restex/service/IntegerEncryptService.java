/*
 * IntegerEncryptService.java
 *
 * @formatter:off
 *
 *
 * @author Andrew
 *
 * Created: 17 Jan 2019
 *
 * @formatter:on
 */

package com.github.awferg.restex.service;

import java.math.BigInteger;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class IntegerEncryptService implements EncryptDecrypt<Integer, String> {

    private final EncryptService encryptService;

    @Inject
    public IntegerEncryptService(final EncryptService encryptService) {
        super();
        this.encryptService = encryptService;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.github.awferg.restex.service.EncryptDecrypt#decrypt(java.lang.Object)
     */
    @Override
    public Integer decrypt(final String input) {

        if (input == null) {
            return null;
        }

        return new BigInteger(this.encryptService.decryptToBytes(input)).intValue();
    }

    /*
     * (non-Javadoc)
     *
     * @see com.github.awferg.restex.service.EncryptDecrypt#encrypt(java.lang.Object)
     */
    @Override
    public String encrypt(final Integer input) {

        if (input == null) {
            return null;
        }

        final BigInteger bigInt = BigInteger.valueOf(input);
        return this.encryptService.encrypt(bigInt.toByteArray());
    }
}
