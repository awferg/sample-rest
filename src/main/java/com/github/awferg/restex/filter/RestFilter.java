/*
 * RestFilter.java
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

package com.github.awferg.restex.filter;

import com.google.json.JsonSanitizer;

import org.slf4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Scanner;

import javax.annotation.Priority;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
@PreMatching
@Priority(Priorities.ENTITY_CODER)
@Dependent
public class RestFilter implements ContainerRequestFilter {

    private static final int MAX_SIZE = 5000; // max size we will allow

    @Inject
    private Logger logger;

    @Override
    public void filter(final ContainerRequestContext containerRequestContext) throws IOException {

        if (this.isJson(containerRequestContext)) {
            final StringBuilder jsonBuilder = new StringBuilder(RestFilter.MAX_SIZE);

            try (@SuppressWarnings("resource")
            Scanner sc =
                    new Scanner(containerRequestContext.getEntityStream()).useDelimiter("\\A")) {
                if (sc.hasNext()) {
                    final String requetBody = sc.next();
                    containerRequestContext
                            .setEntityStream(new ByteArrayInputStream(requetBody.getBytes()));
                    jsonBuilder.append(requetBody);
                    if (jsonBuilder.length() > RestFilter.MAX_SIZE) {
                        logger.warn("Rejected data due to length");
                        containerRequestContext.abortWith(Response
                                .status(Response.Status.BAD_REQUEST)
                                .type(MediaType.TEXT_PLAIN).entity("Invalid length").build());
                        return;
                    }
                }
            }

            // reject anything that looks bad
            final String originalJson = jsonBuilder.toString();
            final String wellFormedJson = JsonSanitizer.sanitize(originalJson);

            if (!wellFormedJson.equals(originalJson)) {
                containerRequestContext.abortWith(Response.status(Response.Status.BAD_REQUEST)
                        .type(MediaType.TEXT_PLAIN).entity("Invalid Json").build());
            }
        }
    }

    private boolean isJson(final ContainerRequestContext request) {

        return request.getMediaType() != null
                && request.getMediaType().equals(MediaType.APPLICATION_JSON_TYPE);
    }
}
