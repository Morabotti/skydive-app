package fi.morabotti.skydive.resources;

import fi.morabotti.skydive.controller.ClubController;
import fi.morabotti.skydive.model.Account;
import fi.morabotti.skydive.model.Activity;
import fi.morabotti.skydive.view.AccountView;
import fi.morabotti.skydive.view.PaginationQuery;
import fi.morabotti.skydive.view.PaginationResponse;
import fi.morabotti.skydive.view.club.ClubCreationRequest;
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
            @Context Account account,
            ClubCreationRequest creationRequest
    ) {
        return clubController.createClub(creationRequest, account);
    }

    @GET
    @Path("{clubSlug}")
    public ClubView getClubBySlug(
            @PathParam("clubSlug") String slug
    ) {
        return clubController.getClub(slug);
    }

    @PUT
    @Path("{clubSlug}")
    public Response updateClub(
            @PathParam("clubSlug") String slug
    ) {
        return Response.ok().build();
    }

    @DELETE
    @Path("{clubSlug}")
    public Response deleteClub(
            @PathParam("clubSlug") String slug
    ) {
        clubController.deleteClub(slug);
        return Response.ok().build();
    }

    @GET
    @Path("/{clubSlug}/activity")
    public List<Activity> getClubActivities(
            @PathParam("clubSlug") String slug
    ) {
        return Collections.emptyList();
    }

    @GET
    @Path("/{clubSlug}/member")
    public PaginationResponse<AccountView> getClubMembers(
            @PathParam("clubSlug") String slug,
            @BeanParam PaginationQuery paginationQuery
    ) {
        return clubController.getMembers(
                paginationQuery,
                slug
        );
    }

    @GET
    @Path("/{clubSlug}/member/{accountId}")
    public AccountView getClubMemberInformation(
            @PathParam("clubSlug") String slug,
            @PathParam("accountId") Long accountId
    ) {
        return clubController.getMember(slug, accountId);
    }

    @POST
    @Path("/{clubSlug}/member/request")
    public AccountView requestClubJoin(
            @PathParam("clubSlug") String slug,
            @Context Account account,
            ClubMemberRequest clubMemberRequest
    ) {
        return clubController.requestMemberToClub(slug, account, clubMemberRequest);
    }

    // TODO: Add check whether Account is actually owner of this club.

    @DELETE
    @Path("/{clubSlug}/member/{accountId}")
    public Response deleteMemberFromClub(
            @PathParam("clubSlug") String slug,
            @PathParam("accountId") Long accountId,
            @Context Account account
    ) {
        clubController.removeMemberFromClub(slug, accountId);
        return Response.ok().build();
    }

    @POST
    @Path("/{clubSlug}/accept/{accountId}")
    public AccountView acceptUserToClub(
            @PathParam("clubSlug") String slug,
            @PathParam("accountId") Long accountId,
            @Context Account account
    ) {
        return clubController.acceptMemberToClub(slug, accountId);
    }

    @POST
    @Path("/{clubSlug}/decline/{accountId}")
    public Response declineUserFromClub(
            @PathParam("clubSlug") String slug,
            @PathParam("accountId") Long accountId,
            @Context Account account
    ) {
        clubController.removeMemberFromClub(slug, accountId);
        return Response.ok().build();
    }
}
