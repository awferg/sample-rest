/*
 * EncryptDecrypt.java
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

public interface EncryptDecrypt<T, E> {

    T decrypt(E input);

    E encrypt(T input);
}
