/*
 * User.java
 *
 * @formatter:off
 *
 * @author Andrew
 *
 * Created: 15 Jan 2019
 *
 * @formatter:on
 */

package com.github.awferg.restex.model;

import java.util.Objects;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class User {

    private int id;

    @NotNull
    @Size(max = 50, min = 1)
    private String firstName;

    @NotNull
    @Size(max = 50, min = 1)
    private String lastName;

    @Size(max = 30, message = "Email is too long it cannot be more than {value} characters")
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$",
            message = "Email isn't valid")
    private String email;

    @Min(value = 0, message = "Age is invalid")
    @Max(value = 150, message = "Value too large for an age")
    private Integer age;

    public User() {
        super();
    }

    public User(final String firstname, final String lastName, final String email, final int age) {
        super();
        this.firstName = firstname;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
    }

    @Override
    public boolean equals(final Object object) {

        if (object instanceof User) {
            final User that = (User) object;
            return this.id == that.id && Objects.equals(this.firstName, that.firstName)
                    && Objects.equals(this.lastName, that.lastName)
                    && Objects.equals(this.email, that.email)
                    && Objects.equals(this.age,that.age);
        }
        return false;
    }

    public Integer getAge() {

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

    public void setAge(final Integer age) {

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
