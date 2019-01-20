/*
 * RestApplication.java
 *
 * @formatter:off
 *
 * JAX-RS setup. Implement methods if custom setting required, this will disable auto scanning.
 *
 * @author Andrew
 *
 * Created: 15 Jan 2019
 *
 * @formatter:on
 */

package com.github.awferg.restex;

import javax.enterprise.context.Dependent;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@Dependent
@ApplicationPath("/api/*")
public class RestApplication extends Application {

}