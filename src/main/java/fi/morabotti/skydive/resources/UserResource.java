package fi.morabotti.skydive.resources;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/user")
@Singleton
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed({"admin"})
public class UserResource {
    @Inject
    public UserResource() {

    }

    @GET
    public Response getUsers() {
        return Response.ok().build();
    }

    @GET
    @Path("{userId}")
    public Response getUserById(
            @PathParam("userId") Long userId
    ) {
        return Response.ok().build();
    }

    @PUT
    @Path("{userId}")
    public Response updateUser(
            @PathParam("userId") Long userId
    ) {
        return Response.ok().build();
    }

    @DELETE
    @Path("{userId}")
    public Response deleteUser(
            @PathParam("userId") Long userId
    ) {
        return Response.ok().build();
    }

    @GET
    @Path("/{userId}/activity")
    public Response getUserActivities(
            @PathParam("userId") Long userId
    ) {
        return Response.ok().build();
    }

    @GET
    @Path("/{userId}/club")
    public Response getUserClubs(
            @PathParam("userId") Long userId
    ) {
        return Response.ok().build();
    }

    @GET
    @Path("/{userId}/jump")
    public Response getUserJumps(
            @PathParam("userId") Long userId
    ) {
        return Response.ok().build();
    }
}
