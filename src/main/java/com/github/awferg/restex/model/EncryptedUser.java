/*
 * EncryptedUser.java
 *
 * @formatter:off
 *
 * @author Andrew
 *
 * Created: 17 Jan 2019
 *
 * @formatter:on
 */

package com.github.awferg.restex.model;

import java.util.Objects;

public class EncryptedUser {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String age;

    public EncryptedUser() {
        super();
    }

    public EncryptedUser(final String firstname, final String lastName, final String email, final String age) {

        super();
        this.firstName = firstname;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
    }

    @Override
    public boolean equals(final Object object) {

        if (object instanceof EncryptedUser) {
            final EncryptedUser that = (EncryptedUser) object;
            return this.id == that.id && Objects.equals(this.firstName, that.firstName)
                    && Objects.equals(this.lastName, that.lastName)
                    && Objects.equals(this.email, that.email) && Objects.equals(this.age, that.age);
        }
        return false;
    }

    public String getAge() {

        return this.age;
    }

    public String getEmail() {

        return this.email;
    }

    public String getFirstName() {

        return this.firstName;
    }

    public int getId() {
        return this.id;
    }

    public String getLastName() {

        return this.lastName;
    }

    @Override
    public int hashCode() {

        return Objects.hash(this.id, this.firstName, this.lastName, this.email, this.age);
    }

    public void setAge(final String age) {

        this.age = age;
    }

    public void setEmail(final String email) {

        this.email = email;
    }

    public void setFirstName(final String firstName) {

        this.firstName = firstName;
    }

    public void setId(final int id) {

        this.id = id;
    }

    public void setLastName(final String lastName) {

        this.lastName = lastName;
    }

    @Override
    public String toString() {

        return "User [id=" + this.id + ", firstname=" + this.firstName + ", lastName="
                + this.lastName + ", email=" + this.email + ", age=" + this.age + "]";
    }
}
