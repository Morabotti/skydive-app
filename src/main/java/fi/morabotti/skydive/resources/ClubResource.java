package fi.morabotti.skydive.resources;

import fi.morabotti.skydive.controller.ClubController;
import fi.morabotti.skydive.model.Account;
import fi.morabotti.skydive.model.Activity;
import fi.morabotti.skydive.view.AccountView;
import fi.morabotti.skydive.view.PaginationQuery;
import fi.morabotti.skydive.view.PaginationResponse;
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
            ClubInformationRequest informationRequest
    ) {
        return clubController.updateClub(clubId, informationRequest);
    }

    @DELETE
    @Path("{clubId}")
    public Response deleteClub(
            @PathParam("clubId") Long clubId
    ) {
        clubController.deleteClub(clubId);
        return Response.ok().build();
    }

    @GET
    @Path("/{clubId}/activity")
    public List<Activity> getClubActivities(
            @PathParam("clubId") Long clubId
    ) {
        return Collections.emptyList();
    }

    @GET
    @Path("/{clubId}/member")
    public PaginationResponse<AccountView> getClubMembers(
            @PathParam("clubId") Long clubId,
            @BeanParam PaginationQuery paginationQuery
    ) {
        return clubController.getMembers(
                paginationQuery,
                clubId
        );
    }

    @GET
    @Path("/{clubId}/member/{accountId}")
    public AccountView getClubMemberInformation(
            @PathParam("clubId") Long clubId,
            @PathParam("accountId") Long accountId
    ) {
        return clubController.getMember(clubId, accountId);
    }

    @DELETE
    @Path("/{clubId}/member/{accountId}")
    public Response deleteMemberFromClub(
            @PathParam("clubId") Long clubId,
            @PathParam("accountId") Long accountId,
            @Context Account account
    ) {
        clubController.removeMemberFromClub(clubId, accountId);
        return Response.ok().build();
    }

    @POST
    @Path("/{clubId}/member/{accountId}")
    @RolesAllowed({"admin"})
    public AccountView addMemberFromClub(
            @PathParam("clubId") Long clubId,
            @PathParam("accountId") Long accountId,
            ClubMemberRequest clubMemberRequest
    ) {
        return clubController.addMemberToClub(clubId, accountId, clubMemberRequest);
    }

    @POST
    @Path("/{clubId}/member/request")
    public AccountView requestClubJoin(
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
    public AccountView acceptUserToClub(
            @PathParam("clubId") Long clubId,
            @PathParam("accountId") Long accountId,
            @Context Account account
    ) {
        return clubController.acceptMemberToClub(clubId, accountId);
    }

    @PUT
    @Path("/{clubId}/decline/{accountId}")
    public Response declineUserFromClub(
            @PathParam("clubId") Long clubId,
            @PathParam("accountId") Long accountId,
            @Context Account account
    ) {
        clubController.removeMemberFromClub(clubId, accountId);
        return Response.ok().build();
    }
}
