package org.acme.endpoint;

import org.acme.service.LikeService;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LikeResource {

    @Inject
    LikeService likeService;

    @POST
    @RolesAllowed("user")
    @Path("/like/{id}")
    @Transactional
    public Response like(@Context SecurityContext securityContext, @PathParam Long id) {
        try {
            likeService.like(securityContext, id);
            return Response.ok().build();
        } catch (BadRequestException e) {
            e.printStackTrace();
            return Response.noContent().build();
        }
    }

    @DELETE
    @RolesAllowed("user")
    @Path("/unlike/{id}")
    @Transactional
    public Response unlike(@Context SecurityContext securityContext, @PathParam Long id) {
        try {
            likeService.unlike(securityContext, id);
            return Response.ok().build();
        } catch (BadRequestException e) {
            e.printStackTrace();
            return Response.noContent().build();
        }
    }
}
