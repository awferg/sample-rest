/*
 * RuntimeExceptionMapper.java
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

import org.slf4j.Logger;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
@Dependent
public class RuntimeExceptionMapper implements ExceptionMapper<RuntimeException> {

    @Inject
    private Logger logger;

    /*
     * (non-Javadoc)
     *
     * @see javax.ws.rs.ext.ExceptionMapper#toResponse(java.lang.Throwable)
     */
    @Override
    public Response toResponse(final RuntimeException exception) {

        if (exception instanceof ClientErrorException) {
            // stop 404's being reported as errors as our mapper is a bit wide in scope
            return Response.status(Status.NOT_FOUND).build();
        }

        // TODO shouldn't send exception to client, generate identifier and send it, log exception against identifier
        this.logger.error("Error encountered", exception);
        return Response.serverError().type(MediaType.TEXT_PLAIN).entity(exception.toString()).build();
    }
}
