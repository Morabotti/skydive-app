package fi.morabotti.skydive.resources;

import fi.morabotti.skydive.model.Account;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/personal")
@Singleton
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PersonalResource {
    @Inject
    public PersonalResource() {

    }

    @GET
    @Path("/club")
    public Response getMyClubs(
            @Context Account account
    ) {
        return Response.ok().build();
    }

    @GET
    @Path("/activity")
    public Response getMyActivities(
            @Context Account account
    ) {
        return Response.ok().build();
    }

    @GET
    @Path("/jump")
    public Response getMyJumps(
            @Context Account account
    ) {
        return Response.ok().build();
    }
}
