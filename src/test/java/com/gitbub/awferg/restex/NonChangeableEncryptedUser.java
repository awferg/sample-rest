/*
 * NonChangeableEncryptedUser.java
 *
 * @formatter:off
 *
 * Class to ensure we aren't changing user and messing up tests.
 *
 * @author Andrew
 *
 * Created: 19 Jan 2019
 *
 * @formatter:on
 */

package com.gitbub.awferg.restex;

import com.github.awferg.restex.model.EncryptedUser;

public class NonChangeableEncryptedUser extends EncryptedUser {

    public NonChangeableEncryptedUser(final String firstname, final String lastName,
            final String email, final String age) {

        super(firstname, lastName, email, age);
    }

    @Override
    public void setAge(final String age) {

        this.raiseError("Age");
    }

    @Override
    public void setEmail(final String email) {

        this.raiseError("Email");
    }

    @Override
    public void setFirstName(final String firstName) {

        this.raiseError("firstName");
    }

    @Override
    public void setId(final int id) {

        this.raiseError("id");
    }

    @Override
    public void setLastName(final String lastName) {

        this.raiseError("lastName");
    }

    private void raiseError(final String field) {

        throw new UnsupportedOperationException("An attemp was made to set " + field);
    }
}
