package fi.morabotti.skydive.resources;

import fi.morabotti.skydive.model.Activity;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Collections;
import java.util.List;

@Path("/activity")
@Singleton
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ActivityResource {

    @Inject
    public ActivityResource() {

    }

    @GET
    public List<Activity> getActivities() {
        return Collections.emptyList();
    }

    @POST
    public Activity createActivity() {
        return Activity.builder().build();
    }

    @GET
    @Path("{token}")
    public Activity getActivity(
            @PathParam("token") String token
    ) {
        return Activity.builder().build();
    }

    @DELETE
    @Path("{token}")
    public Boolean deleteActivity(
            @PathParam("token") String token
    ) {
        return true;
    }

    @PUT
    @Path("{token}")
    public Activity updateActivity(
            @PathParam("token") String token
    ) {
        return Activity.builder().build();
    }

    @GET
    @Path("/{token}/member")
    public List getActivityMembers(
            @PathParam("token") String token
    ) {
        return Collections.emptyList();
    }
}
