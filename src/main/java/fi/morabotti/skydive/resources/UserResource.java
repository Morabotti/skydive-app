package fi.morabotti.skydive.resources;

import fi.morabotti.skydive.controller.AccountController;
import fi.morabotti.skydive.controller.ActivityController;
import fi.morabotti.skydive.controller.ClubController;
import fi.morabotti.skydive.view.AccountView;
import fi.morabotti.skydive.view.DateRangeQuery;
import fi.morabotti.skydive.view.PaginationQuery;
import fi.morabotti.skydive.view.PaginationResponse;
import fi.morabotti.skydive.view.activity.PersonalActivityView;
import fi.morabotti.skydive.view.auth.UpdateRequest;
import fi.morabotti.skydive.view.auth.UserQuery;
import fi.morabotti.skydive.view.club.ClubAccountView;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/user")
@Singleton
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed({"admin"})
public class UserResource {
    private final AccountController accountController;
    private final ClubController clubController;
    private final ActivityController activityController;

    @Inject
    public UserResource(
            AccountController accountController,
            ClubController clubController,
            ActivityController activityController
    ) {
        this.accountController = accountController;
        this.clubController = clubController;
        this.activityController = activityController;
    }

    @GET
    public PaginationResponse<AccountView> getUsers(
            @BeanParam PaginationQuery paginationQuery,
            @BeanParam UserQuery userQuery
    ) {
        return accountController.getAccounts(paginationQuery, userQuery);
    }

    @GET
    @Path("{accountId}")
    public AccountView getUserById(
            @PathParam("accountId") Long accountId
    ) {
        return accountController.getAccount(accountId);
    }

    @PUT
    @Path("{accountId}")
    public AccountView updateUser(
            @PathParam("accountId") Long accountId,
            UpdateRequest updateRequest
    ) {
        return accountController.updateUser(accountId, updateRequest);
    }

    @DELETE
    @Path("{accountId}")
    public Response deleteUser(
            @PathParam("accountId") Long accountId
    ) {
        accountController.deleteAccount(accountId);
        return Response.ok().build();
    }

    @GET
    @Path("/{accountId}/activity")
    public PersonalActivityView getUserActivities(
            @BeanParam DateRangeQuery rangeQuery,
            @PathParam("accountId") Long accountId
    ) {
        return activityController.getAccountsActivities(rangeQuery, accountId);
    }

    @GET
    @Path("/{accountId}/club")
    public List<ClubAccountView> getUserClubs(
            @PathParam("accountId") Long accountId
    ) {
        return clubController.getAccountClubs(accountId);
    }

    @GET
    @Path("/{accountId}/jump")
    public Response getUserJumps(
            @PathParam("accountId") Long accountId
    ) {
        return Response.ok().build();
    }
}
