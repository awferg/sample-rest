/*
 * NonChangeableUser.java
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

import com.github.awferg.restex.model.User;

public class NonChangeableUser extends User {

    public NonChangeableUser(final String firstname, final String lastName,
            final String email, final int age) {

        super(firstname, lastName, email, age);
    }

    @Override
    public void setAge(final Integer age) {

        this.raiseError("age");
    }

    @Override
    public void setEmail(final String email) {

        this.raiseError("email");
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

        throw new UnsupportedOperationException("An attempt was made to set " + field);
    }
}
