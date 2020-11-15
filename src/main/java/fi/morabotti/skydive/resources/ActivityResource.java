package fi.morabotti.skydive.resources;

import fi.morabotti.skydive.controller.ActivityController;
import fi.morabotti.skydive.model.Account;
import fi.morabotti.skydive.model.Activity;
import fi.morabotti.skydive.view.DateRangeQuery;
import fi.morabotti.skydive.view.PaginationQuery;
import fi.morabotti.skydive.view.PaginationResponse;
import fi.morabotti.skydive.view.activity.ActivityView;
import fi.morabotti.skydive.view.activity.CreateActivityRequest;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Path("/activity")
@Singleton
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ActivityResource {
    private final ActivityController activityController;

    @Inject
    public ActivityResource(
            ActivityController activityController
    ) {
        this.activityController = activityController;
    }

    @GET
    public PaginationResponse<ActivityView> getActivities(
            @BeanParam PaginationQuery paginationQuery,
            @BeanParam DateRangeQuery dateRangeQuery
    ) {
        return activityController.getActivities(
                paginationQuery,
                dateRangeQuery
        );
    }

    @POST
    public ActivityView createActivity(
            @Context Account account,
            CreateActivityRequest activityRequest
    ) {
        return activityController.createActivity(
                activityRequest,
                account
        );
    }

    @GET
    @Path("{activityId}")
    public ActivityView getActivityById(
            @PathParam("activityId") Long id
    ) {
        return activityController.getActivity(id);
    }

    @GET
    @Path("/token/{token}")
    public ActivityView getActivityByToken(
            @PathParam("token") String token
    ) {
        return activityController.getActivity(UUID.fromString(token));
    }

    @DELETE
    @Path("{activityId}")
    public Boolean deleteActivity(
            @PathParam("activityId") Long id
    ) {
        return true;
    }

    @PUT
    @Path("{activityId}")
    public Activity updateActivity(
            @PathParam("activityId") Long id
    ) {
        return Activity.builder().build();
    }

    @GET
    @Path("/{activityId}/member")
    public List getActivityMembers(
            @PathParam("token") String token
    ) {
        return Collections.emptyList();
    }
}
