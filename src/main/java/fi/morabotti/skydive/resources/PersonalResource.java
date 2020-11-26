package fi.morabotti.skydive.resources;

import fi.morabotti.skydive.controller.ActivityController;
import fi.morabotti.skydive.controller.ClubController;
import fi.morabotti.skydive.model.Account;
import fi.morabotti.skydive.view.DateRangeQuery;
import fi.morabotti.skydive.view.activity.PersonalActivityView;
import fi.morabotti.skydive.view.club.ClubAccountView;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/personal")
@Singleton
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PersonalResource {
    private final ClubController clubController;
    private final ActivityController activityController;

    @Inject
    public PersonalResource(
            ClubController clubController,
            ActivityController activityController
    ) {
        this.clubController = clubController;
        this.activityController = activityController;
    }

    @GET
    @Path("/club")
    public List<ClubAccountView> getMyClubs(
            @Context Account account
    ) {
        return clubController.getAccountClubs(account.getId());
    }

    @GET
    @Path("/activity")
    public PersonalActivityView getMyActivities(
            @BeanParam DateRangeQuery rangeQuery,
            @Context Account account
    ) {
        return activityController.getAccountsActivities(rangeQuery, account.getId());
    }

    @GET
    @Path("/jump")
    public Response getMyJumps(
            @Context Account account
    ) {
        return Response.ok().build();
    }
}
