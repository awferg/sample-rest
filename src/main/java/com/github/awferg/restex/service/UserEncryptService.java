/*
 * UserEncryptService.java
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

import com.github.awferg.restex.model.EncryptedUser;
import com.github.awferg.restex.model.User;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class UserEncryptService implements EncryptDecrypt<User, EncryptedUser> {

    private final EncryptService encryptService;
    private final IntegerEncryptService integerEncryptService;

    @Inject
    public UserEncryptService(final EncryptService encryptService,
            final IntegerEncryptService integerEncryptService) {
        super();
        this.encryptService = encryptService;
        this.integerEncryptService = integerEncryptService;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.github.awferg.restex.service.EncryptDecrypt#decrypt(java.lang.Object)
     */
    @Override
    public User decrypt(final EncryptedUser input) {

        if (input == null) {
            return null;
        }

        final User user = new User();

        user.setId(input.getId());
        user.setAge(this.integerEncryptService.decrypt(input.getAge()));
        user.setEmail(this.encryptService.decrypt(input.getEmail()));
        user.setFirstName(this.encryptService.decrypt(input.getFirstName()));
        user.setLastName(this.encryptService.decrypt(input.getLastName()));

        return user;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.github.awferg.restex.service.EncryptDecrypt#encrypt(java.lang.Object)
     */
    @Override
    public EncryptedUser encrypt(final User input) {

        if (input == null) {
            return null;
        }

        final EncryptedUser encryptedUser = new EncryptedUser();

        encryptedUser.setId(input.getId());
        encryptedUser.setAge(this.integerEncryptService.encrypt(input.getAge()));
        encryptedUser.setEmail(this.encryptService.encrypt(input.getEmail()));
        encryptedUser.setFirstName(this.encryptService.encrypt(input.getFirstName()));
        encryptedUser.setLastName(this.encryptService.encrypt(input.getLastName()));

        return encryptedUser;
    }
}
