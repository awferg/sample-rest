/*
 * ConstrainViolationsMapperTest.java
 *
 * @formatter:off
 *
 *
 * @author Andrew
 *
 * Created: 20 Jan 2019
 *
 * @formatter:on
 */

package com.gitbub.awferg.restex.error;

import com.github.awferg.restex.error.ConstrainViolationsMapper;
import com.github.awferg.restex.error.ConstrainViolationsMapper.Violation;
import com.github.awferg.restex.model.User;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class ConstrainViolationsMapperTest {

    private Validator validator;
    private ConstrainViolationsMapper constrainViolationsMapper;
    private ConstraintViolationException constraintException;

    @Test
    public void getViolationsContainsEmailField() {

        final List<Violation> mappedViolations =
                this.constrainViolationsMapper.getViolations(this.constraintException);

        Assert.assertTrue("Email mapping not found",
                this.hasViolationsGotField(mappedViolations, "email"));
    }

    @Test
    public void getViolationsContainsNullMessage() {

        final List<Violation> mappedViolations =
                this.constrainViolationsMapper.getViolations(this.constraintException);

        Assert.assertTrue("Null message not found",
                this.hasViolationsGotMessage(mappedViolations, "may not be null"));
    }

    @Test
    public void getViolationsSameSize() {

        final List<Violation> mappedViolations =
                this.constrainViolationsMapper.getViolations(this.constraintException);

        Assert.assertEquals(this.constraintException.getConstraintViolations().size(),
                mappedViolations.size());
    }

    @Before
    public void setUp() {

        final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
        this.constrainViolationsMapper = new ConstrainViolationsMapper();

        // set up some validation errors
        final User user = new User();
        user.setEmail("Andrew.Ferguson.com");
        user.setAge(200);

        final Set<ConstraintViolation<User>> userViolations = this.validator.validate(user);

        this.constraintException = new ConstraintViolationException(userViolations);
    }

    private boolean hasViolationsGotField(final List<Violation> mappedViolations,
            final String fieldName) {

        return mappedViolations.stream().anyMatch(v -> v.getField().equals(fieldName));
    }

    private boolean hasViolationsGotMessage(final List<Violation> mappedViolations,
            final String message) {

        return mappedViolations.stream().anyMatch(v -> v.getMessage().equals(message));
    }
}
