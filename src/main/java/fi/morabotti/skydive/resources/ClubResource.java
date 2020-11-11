package fi.morabotti.skydive.resources;

import fi.morabotti.skydive.controller.ClubController;
import fi.morabotti.skydive.model.Club;
import fi.morabotti.skydive.model.ClubActivity;
import fi.morabotti.skydive.view.AccountView;

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
    public Club createNewClub() {
        return Club.builder().build();
    }

    @GET
    @Path("{clubId}")
    public Club getClubById(
            @PathParam("clubId") Long clubId
    ) {
        return Club.builder().build();
    }

    @PUT
    @Path("{clubId}")
    @RolesAllowed({"admin", "club"})
    public Club updateClub(
            @PathParam("clubId") Long clubId
    ) {
        return Club.builder().build();
    }

    @DELETE
    @Path("{clubId}")
    @RolesAllowed({"admin", "club"})
    public Club deleteClub(
            @PathParam("clubId") Long clubId
    ) {
        return Club.builder().build();
    }

    @GET
    @Path("/{clubId}/activity")
    public List<ClubActivity> getClubActivities(
            @PathParam("clubId") Long clubId
    ) {
        return Collections.emptyList();
    }

    @GET
    @Path("/{clubId}/activity/{activityId}")
    public ClubActivity getClubActivityById(
            @PathParam("clubId") Long clubId,
            @PathParam("activityId") Long activityId
    ) {
        return ClubActivity.builder().build();
    }

    @POST
    @Path("/{clubId}/activity")
    @RolesAllowed({"admin", "club"})
    public ClubActivity createClubActivity(
            @PathParam("clubId") Long clubId
    ) {
        return ClubActivity.builder().build();
    }

    @PUT
    @Path("/{clubId}/activity/{activityId}")
    @RolesAllowed({"admin", "club"})
    public ClubActivity updateClubActivity(
            @PathParam("clubId") Long clubId,
            @PathParam("activityId") Long activityId
    ) {
        return ClubActivity.builder().build();
    }

    @DELETE
    @Path("/{clubId}/activity/{activityId}")
    @RolesAllowed({"admin", "club"})
    public ClubActivity deleteClubActivity(
            @PathParam("clubId") Long clubId,
            @PathParam("activityId") Long activityId
    ) {
        return ClubActivity.builder().build();
    }

    @GET
    @Path("/{clubId}/member")
    public List<AccountView> getClubMembersById(
            @PathParam("clubId") Long clubId
    ) {
        return Collections.emptyList();
    }

    @GET
    @Path("/{clubId}/member/{memberId}")
    public AccountView getClubMemberInformation(
            @PathParam("clubId") Long clubId,
            @PathParam("memberId") Long memberId
    ) {
        return AccountView.builder().build();
    }

    @DELETE
    @Path("/{clubId}/member/{memberId}")
    @RolesAllowed({"admin", "club"})
    public AccountView deleteMemberFromClub(
            @PathParam("clubId") Long clubId,
            @PathParam("memberId") Long memberId
    ) {
        return AccountView.builder().build();
    }

    @POST
    @Path("/{clubId}/accept/{memberId}")
    @RolesAllowed({"admin", "club"})
    public AccountView addUserToClub(
            @PathParam("clubId") Long clubId,
            @PathParam("memberId") Long memberId
    ) {
        return AccountView.builder().build();
    }

    @POST
    @Path("/{clubId}/decline/{memberId}")
    @RolesAllowed({"admin", "club"})
    public AccountView declineUserFromClub(
            @PathParam("clubId") Long clubId,
            @PathParam("memberId") Long memberId
    ) {
        return AccountView.builder().build();
    }
}
