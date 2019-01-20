package com.gitbub.awferg.restex.model;

import com.github.awferg.restex.model.User;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class UserTest {

    private Validator validator;

    @Before
    public void setUp() {

        final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Test
    public void testInvalidEmailCausesOneViolation() {

        final User user = this.getBaseUser();
        user.setEmail("Andrew.Ferguson.com");

        final Set<ConstraintViolation<User>> violations = this.validator.validate(user);

        Assert.assertEquals(1, violations.size());
    }

    @Test
    public void testInvalidEmailUserAllFields() {

        final User user = this.getBaseUser();
        user.setEmail("Andrew.Ferguson.com");

        final Set<ConstraintViolation<User>> violations = this.validator.validate(user);

        Assert.assertEquals("Email isn't valid",
                violations.iterator().next().getMessage());
    }

    @Test
    public void testInvalidFirstNameTooLong() {

        final User user = this.getBaseUser();
        user.setFirstName("Andrew-------------------------------------------51");

        final Set<ConstraintViolation<User>> violations = this.validator.validate(user);

        Assert.assertEquals(violations.size(), 1);
    }

    @Test
    public void testValidUserAllFields() {

        final Set<ConstraintViolation<User>> violations =
                this.validator.validate(this.getBaseUser());

        Assert.assertTrue(violations.isEmpty());
    }

    private User getBaseUser() {

        return new User("Andrew", "Ferguson", "andrew.ferguson@somehere.com", 10);
    }
}
