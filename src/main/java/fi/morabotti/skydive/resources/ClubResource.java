package fi.morabotti.skydive.resources;

import fi.morabotti.skydive.controller.ActivityController;
import fi.morabotti.skydive.controller.ClubController;
import fi.morabotti.skydive.model.Account;
import fi.morabotti.skydive.model.Plane;
import fi.morabotti.skydive.view.DateRangeQuery;
import fi.morabotti.skydive.view.PaginationQuery;
import fi.morabotti.skydive.view.PaginationResponse;
import fi.morabotti.skydive.view.activity.ActivityQuery;
import fi.morabotti.skydive.view.activity.ActivityView;
import fi.morabotti.skydive.view.club.ClubAccountView;
import fi.morabotti.skydive.view.club.ClubInformationRequest;
import fi.morabotti.skydive.view.club.ClubMemberRequest;
import fi.morabotti.skydive.view.club.ClubQuery;
import fi.morabotti.skydive.view.club.ClubView;

import javax.annotation.security.RolesAllowed;
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
import java.util.List;

@Path("/club")
@Singleton
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClubResource {
    private final ClubController clubController;
    private final ActivityController activityController;

    @Inject
    public ClubResource(
            ClubController clubController,
            ActivityController activityController
    ) {
        this.clubController = clubController;
        this.activityController = activityController;
    }

    @GET
    public PaginationResponse<ClubView> getClubs(
            @BeanParam PaginationQuery paginationQuery,
            @BeanParam ClubQuery clubQuery,
            @Context Account account
    ) {
        return clubController.getClubs(paginationQuery, clubQuery, account);
    }

    @POST
    @RolesAllowed({"admin"})
    public ClubView createNewClub(
            ClubInformationRequest informationRequest
    ) {
        return clubController.createClub(informationRequest);
    }

    @GET
    @Path("{clubId}")
    public ClubView getClubById(
            @PathParam("clubId") Long clubId
    ) {
        return clubController.getClub(clubId);
    }

    @GET
    @Path("/slug/{clubSlug}")
    public ClubView getClubBySlug(
            @PathParam("clubSlug") String clubSlug
    ) {
        return clubController.getClub(clubSlug);
    }

    @PUT
    @Path("{clubId}")
    public ClubView updateClub(
            @PathParam("clubId") Long clubId,
            @Context Account account,
            ClubInformationRequest informationRequest
    ) {
        return clubController.updateClub(clubId, informationRequest, account);
    }

    @DELETE
    @Path("{clubId}")
    public Response deleteClub(
            @PathParam("clubId") Long clubId,
            @Context Account account
    ) {
        clubController.deleteClub(clubId, account);
        return Response.ok().build();
    }

    @GET
    @Path("/{clubId}/activity")
    public PaginationResponse<ActivityView> getClubActivities(
            @BeanParam PaginationQuery paginationQuery,
            @BeanParam DateRangeQuery dateRangeQuery,
            @BeanParam ActivityQuery activityQuery,
            @Context Account account,
            @PathParam("clubId") Long clubId
    ) {
        return activityController.getClubActivities(
                paginationQuery,
                dateRangeQuery,
                activityQuery,
                clubId,
                account
        );
    }

    @GET
    @Path("/{clubId}/plane")
    public List<Plane> getPlanes(
            @PathParam("clubId") Long clubId,
            @Context Account account
    ) {
        return clubController.getPlanes(clubId, account);
    }

    @GET
    @Path("/{clubId}/plane/{planeId}")
    public Plane getPlane(
            @PathParam("clubId") Long clubId,
            @PathParam("planeId") Long planeId,
            @Context Account account
    ) {
        return clubController.getPlane(planeId, clubId, account);
    }

    @POST
    @Path("/{clubId}/plane")
    public Plane createPlane(
            @PathParam("clubId") Long clubId,
            @Context Account account,
            Plane plane
    ) {
        return clubController.createPlane(clubId, account, plane);
    }

    @PUT
    @Path("/{clubId}/plane/{planeId}")
    public Plane updatePlane(
            @PathParam("clubId") Long clubId,
            @PathParam("planeId") Long planeId,
            @Context Account account,
            Plane plane
    ) {
        return clubController.updatePlane(clubId, account, plane);
    }

    @DELETE
    @Path("/{clubId}/plane/{planeId}")
    public Response deletePlane(
            @PathParam("clubId") Long clubId,
            @PathParam("planeId") Long planeId,
            @Context Account account
    ) {
        clubController.deletePlane(planeId, clubId, account);
        return Response.ok().build();
    }

    @GET
    @Path("/{clubId}/member")
    public PaginationResponse<ClubAccountView> getClubMembers(
            @PathParam("clubId") Long clubId,
            @BeanParam PaginationQuery paginationQuery,
            @Context Account account
    ) {
        return clubController.getMembers(
                paginationQuery,
                clubId,
                account
        );
    }

    @GET
    @Path("/{clubId}/member/{accountId}")
    public ClubAccountView getClubMemberInformation(
            @PathParam("clubId") Long clubId,
            @PathParam("accountId") Long accountId,
            @Context Account account
    ) {
        return clubController.getMember(clubId, accountId, account);
    }

    @DELETE
    @Path("/{clubId}/member/{accountId}")
    public Response deleteMemberFromClub(
            @PathParam("clubId") Long clubId,
            @PathParam("accountId") Long accountId,
            @Context Account account
    ) {
        clubController.removeMemberFromClub(clubId, accountId, account, true);
        return Response.ok().build();
    }

    @POST
    @Path("/{clubId}/member/{accountId}")
    @RolesAllowed({"admin"})
    public ClubAccountView addMemberFromClub(
            @PathParam("clubId") Long clubId,
            @PathParam("accountId") Long accountId,
            ClubMemberRequest clubMemberRequest
    ) {
        return clubController.addMemberToClub(clubId, accountId, clubMemberRequest);
    }

    @POST
    @Path("/{clubId}/member/request")
    public ClubAccountView requestClubJoin(
            @PathParam("clubId") Long clubId,
            @Context Account account,
            ClubMemberRequest clubMemberRequest
    ) {
        return clubController.requestMemberToClub(
                clubId,
                account,
                clubMemberRequest
        );
    }

    @PUT
    @Path("/{clubId}/accept/{accountId}")
    public ClubAccountView acceptUserToClub(
            @PathParam("clubId") Long clubId,
            @PathParam("accountId") Long accountId,
            @Context Account account
    ) {
        return clubController.acceptMemberToClub(clubId, accountId, account);
    }

    @PUT
    @Path("/{clubId}/decline/{accountId}")
    public Response declineUserFromClub(
            @PathParam("clubId") Long clubId,
            @PathParam("accountId") Long accountId,
            @Context Account account
    ) {
        clubController.removeMemberFromClub(clubId, accountId, account, false);
        return Response.ok().build();
    }
}
