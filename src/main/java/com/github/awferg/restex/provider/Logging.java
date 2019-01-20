/*
 * Logging.java
 *
 * @formatter:off
 *
 * @author Andrew
 *
 * Created: 15 Jan 2019
 *
 * @formatter:on
 */

package com.github.awferg.restex.provider;

import org.slf4j.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

@ApplicationScoped
public class Logging {

    /**
     * Produces a logger instance.
     *
     * @param injectionPoint
     *            point where Logger defined
     * @return Logger named for class with injection point
     */
    @Produces
    @Dependent
    @Default
    public Logger getLogger(final InjectionPoint injectionPoint) {

        final Class<?> loggerClass = injectionPoint.getMember().getDeclaringClass();

        return org.slf4j.LoggerFactory.getLogger(loggerClass);
    }
}
