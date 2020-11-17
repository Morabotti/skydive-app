package fi.morabotti.skydive.resources;

import fi.morabotti.skydive.controller.ActivityController;
import fi.morabotti.skydive.model.Account;
import fi.morabotti.skydive.model.ActivityParticipation;
import fi.morabotti.skydive.model.PilotActivityParticipation;
import fi.morabotti.skydive.view.DateRangeQuery;
import fi.morabotti.skydive.view.PaginationQuery;
import fi.morabotti.skydive.view.PaginationResponse;
import fi.morabotti.skydive.view.activity.ActivityInformationRequest;
import fi.morabotti.skydive.view.activity.ActivityParticipationView;
import fi.morabotti.skydive.view.activity.ActivityView;

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
import javax.ws.rs.core.Response;
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
        return activityController.getAllActivities(
                paginationQuery,
                dateRangeQuery
        );
    }

    @POST
    public ActivityView createActivity(
            @Context Account account,
            ActivityInformationRequest informationRequest
    ) {
        return activityController.createActivity(
                informationRequest,
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
    public Response deleteActivity(
            @Context Account account,
            @PathParam("activityId") Long id
    ) {
        activityController.deleteActivity(id, account);
        return Response.ok().build();
    }

    @PUT
    @Path("{activityId}")
    public ActivityView updateActivity(
            @PathParam("activityId") Long id,
            @Context Account account,
            ActivityInformationRequest informationRequest
    ) {
        return activityController.updateActivity(
                id,
                informationRequest,
                account
        );
    }

    @GET
    @Path("/{activityId}/member")
    public ActivityParticipationView getActivityMembers(
            @PathParam("activityId") Long id
    ) {
        return activityController.getParticipants(id);
    }

    @POST
    @Path("{activityId}/participate/user")
    public ActivityParticipation participateInActivityAsUser(
            @PathParam("activityId") Long id,
            @Context Account account
    ) {
        return activityController.joinActivityAsUser(account, id, false);
    }

    @POST
    @Path("{activityId}/participate/pilot/{planeId}")
    public PilotActivityParticipation participateInActivityAsPilot(
            @PathParam("activityId") Long activityId,
            @PathParam("planeId") Long planeId,
            @Context Account account
    ) {
        return activityController.joinActivityAsPilot(
                account,
                activityId,
                planeId,
                false
        );
    }
}
