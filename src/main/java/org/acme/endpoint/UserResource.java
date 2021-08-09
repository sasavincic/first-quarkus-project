package org.acme.endpoint;

import org.acme.entity.User;
import org.acme.service.UserService;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Path("/user")
@Produces (MediaType.APPLICATION_JSON)
@Consumes (MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    UserService userService;

    @POST
    @Path("/signup")
    @Transactional
    public Response signup(User user) {
        try{
            userService.signup(user);
            return Response.ok().build();
        } catch (BadRequestException e) {
            e.printStackTrace();
            return Response.noContent().build();
        }
    }

    @GET
    @Path("/{id}")
    public User getById(@PathParam("id") Long id) {
        return userService.findById(id);
    }

    @GET
    @RolesAllowed("user")
    @Path("/me")
    public User me(@Context SecurityContext securityContext) {
        return userService.getMe(securityContext);
    }

    @PUT
    @RolesAllowed("user")
    @Path("/me/update-password")
    @Consumes (MediaType.TEXT_PLAIN)
    @Transactional
    public Response updatePassword(@Context SecurityContext securityContext, String newPassword) {
        userService.updatePassword(securityContext, newPassword);
        return Response.ok().build();
    }

    @GET
    @PermitAll
    @Path("/most-liked")
    public Object getMostLiked(){
        return userService.getMostLiked();
    }

}