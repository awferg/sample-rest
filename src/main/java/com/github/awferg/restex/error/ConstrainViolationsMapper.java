/*
 * ConstrainViolationsMapper.java
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

package com.github.awferg.restex.error;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.Dependent;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path.Node;

@Dependent
public class ConstrainViolationsMapper {

    public static class Violation {

        private final String field;
        private final String message;

        public Violation(final String field, final String message) {

            this.field = field;
            this.message = message;
        }

        public String getMessage() {

            return this.message;
        }

        public String getField() {

            return this.field;
        }

        @Override
        public String toString() {

            return "Violation [field=" + this.field + ", message=" + this.message + "]";
        }
    }

    public List<Violation> getViolations(final ConstraintViolationException exception) {

        return exception.getConstraintViolations().stream().map(this::produceViolation)
                .collect(Collectors.toList());
    }

    private Violation produceViolation(final ConstraintViolation<?> constraintViolation) {

        final Iterator<Node> nodeIterator = constraintViolation.getPropertyPath().iterator();

        Node lastNode = null;

        while (nodeIterator.hasNext()) {
            lastNode = nodeIterator.next();
        }

        return new Violation(lastNode.getName(), constraintViolation.getMessage());
    }
}
