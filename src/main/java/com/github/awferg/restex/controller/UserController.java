/*
 * UserController.java
 *
 * @formatter:off
 *
 * @author Andrew
 *
 * Created: 15 Jan 2019
 *
 * @formatter:on
 */

package com.github.awferg.restex.controller;

import com.github.awferg.restex.model.User;
import com.github.awferg.restex.service.UserService;

import org.slf4j.Logger;

import java.net.URI;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

@RequestScoped
@Path("/users")
public class UserController {

    private final Logger logger;
    private final UserService userService;

    public UserController() {

        this(null, null);
    }

    @Inject
    public UserController(final UserService userService, final Logger logger) {

        this.userService = userService;
        this.logger = logger;
    }

    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUser(final User user, @Context final UriInfo uriInfo) {

        final int userId = this.userService.add(user);

        final UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(Integer.toString(userId));
        final URI location = builder.build();

        this.logger.debug("{} added as id {} and location {} returned", user, userId,
                location);

        return Response.created(location).build();
    }

    @DELETE
    @Path("/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUser(@PathParam("userId") final int userCode) {

        this.userService.delete(userCode);

        return Response.noContent().build();
    }

    @GET
    @Path("/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("userId") final int userCode) {

        User user = this.userService.find(userCode);

        if (user == null) {
            return Response.status(Status.NOT_FOUND).build();
        }

        return Response.ok(user).build();
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers(@DefaultValue("false") @QueryParam("encrypted") boolean encrypted) {

        final List<?> users;

        if (encrypted) {
            // get raw values
            users = this.userService.getAllEncrypted();
        } else {
            users = this.userService.getAll();
        }

        return Response.ok(users).build();
    }
}
