/*
 * ConstraintViolationExceptionMapper.java
 *
 * @formatter:off
 *
 *
 * @author Andrew
 *
 * Created: 19 Jan 2019
 *
 * @formatter:on
 */

package com.github.awferg.restex.error;

import com.github.awferg.restex.error.ConstrainViolationsMapper.Violation;

import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
@Dependent
public class ConstraintViolationExceptionMapper
        implements ExceptionMapper<ConstraintViolationException> {

    @Inject
    private ConstrainViolationsMapper constrainViolationsMapper;

    /*
     * (non-Javadoc)
     *
     * @see javax.ws.rs.ext.ExceptionMapper#toResponse(java.lang.Throwable)
     */
    @Override
    public Response toResponse(final ConstraintViolationException exception) {

        final List<Violation> violations = this.constrainViolationsMapper.getViolations(exception);

        // TODO 422 ?
        return Response.status(Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON)
                .entity(violations).build();
    }
}
