package fi.morabotti.skydive.resources;

import fi.morabotti.skydive.controller.ClubController;
import fi.morabotti.skydive.model.Account;
import fi.morabotti.skydive.model.Club;
import fi.morabotti.skydive.model.ClubActivity;
import fi.morabotti.skydive.view.AccountView;
import fi.morabotti.skydive.view.club.ClubCreationRequest;

import javax.annotation.security.RolesAllowed;
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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.Collections;
import java.util.List;

@Path("/club")
@Singleton
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClubResource {
    private final ClubController clubController;

    @Inject
    public ClubResource(
            ClubController clubController
    ) {
        this.clubController = clubController;
    }

    @GET
    public List getClubs() {
        return Collections.emptyList();
    }

    @POST
    @RolesAllowed({"admin"})
    public Club createNewClub(
            @Context Account account,
            ClubCreationRequest creationRequest
    ) {
        return clubController.createClub(creationRequest, account);
    }

    @GET
    @Path("{club-slug}")
    public Club getClubById(
            @PathParam("club-slug") String slug
    ) {
        return Club.builder().build();
    }

    @PUT
    @Path("{club-slug}")
    public Club updateClub(
            @PathParam("club-slug") String slug
    ) {
        return Club.builder().build();
    }

    @DELETE
    @Path("{club-slug}")
    public Club deleteClub(
            @PathParam("club-slug") String slug
    ) {
        return Club.builder().build();
    }

    @GET
    @Path("/{club-slug}/activity")
    public List<ClubActivity> getClubActivities(
            @PathParam("club-slug") String slug
    ) {
        return Collections.emptyList();
    }

    @GET
    @Path("/{club-slug}/activity/{activityId}")
    public ClubActivity getClubActivityById(
            @PathParam("club-slug") String slug,
            @PathParam("activityId") Long activityId
    ) {
        return ClubActivity.builder().build();
    }

    @POST
    @Path("/{club-slug}/activity")
    public ClubActivity createClubActivity(
            @PathParam("club-slug") String slug
    ) {
        return ClubActivity.builder().build();
    }

    @PUT
    @Path("/{club-slug}/activity/{activityId}")
    public ClubActivity updateClubActivity(
            @PathParam("club-slug") String slug,
            @PathParam("activityId") Long activityId
    ) {
        return ClubActivity.builder().build();
    }

    @DELETE
    @Path("/{club-slug}/activity/{activityId}")
    public ClubActivity deleteClubActivity(
            @PathParam("club-slug") String slug,
            @PathParam("activityId") Long activityId
    ) {
        return ClubActivity.builder().build();
    }

    @GET
    @Path("/{club-slug}/member")
    public List<AccountView> getClubMembersById(
            @PathParam("club-slug") String slug
    ) {
        return Collections.emptyList();
    }

    @GET
    @Path("/{club-slug}/member/{memberId}")
    public AccountView getClubMemberInformation(
            @PathParam("club-slug") String slug,
            @PathParam("memberId") Long memberId
    ) {
        return AccountView.builder().build();
    }

    @DELETE
    @Path("/{club-slug}/member/{memberId}")
    public AccountView deleteMemberFromClub(
            @PathParam("club-slug") String slug,
            @PathParam("memberId") Long memberId
    ) {
        return AccountView.builder().build();
    }

    @POST
    @Path("/{club-slug}/accept/{memberId}")
    public AccountView addUserToClub(
            @PathParam("club-slug") String slug,
            @PathParam("memberId") Long memberId
    ) {
        return AccountView.builder().build();
    }

    @POST
    @Path("/{club-slug}/decline/{memberId}")
    public AccountView declineUserFromClub(
            @PathParam("club-slug") String slug,
            @PathParam("memberId") Long memberId
    ) {
        return AccountView.builder().build();
    }
}
