package fi.morabotti.skydive.resources;

import fi.morabotti.skydive.controller.AccountController;
import fi.morabotti.skydive.db.enums.AccountRole;
import fi.morabotti.skydive.model.Account;
import fi.morabotti.skydive.view.AccountView;
import fi.morabotti.skydive.view.TokenResponse;
import fi.morabotti.skydive.view.auth.ChangePasswordRequest;
import fi.morabotti.skydive.view.auth.LoginRequest;
import fi.morabotti.skydive.view.auth.RegisterRequest;
import fi.morabotti.skydive.view.auth.UpdateRequest;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

@Path("/auth")
@Singleton
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@PermitAll
public class AuthenticationResource {
    private final AccountController accountController;

    @Inject
    public AuthenticationResource(AccountController accountController) {
        this.accountController = accountController;
    }

    @POST
    @Path("/login")
    public TokenResponse authenticate(LoginRequest loginRequest) {
        try {
            return accountController.authenticate(
                    loginRequest.getUsername(),
                    loginRequest.getPassword()
            );
        }
        catch (Exception e) {
            throw new NotAuthorizedException("Could not authorize user");
        }
    }

    @POST
    @Path("/logout")
    public Boolean logout(
            @HeaderParam(HttpHeaders.AUTHORIZATION) String authorization
    ) {
        return accountController.logout(authorization);
    }

    @POST
    @Path("/register")
    public AccountView registerUser(
            RegisterRequest registerRequest
    ) {
        return accountController.createAccount(registerRequest, AccountRole.user);
    }

    @PUT
    @Path("/profile")
    public AccountView updateProfile(
            @Context Account account,
            UpdateRequest updateRequest
    ) {
        return accountController.updateUser(account.getId(), updateRequest);
    }

    @PUT
    @Path("/password")
    public AccountView changePassword(
            @Context Account account,
            ChangePasswordRequest passwordRequest
    ) {
        return accountController.updatePassword(
                account.getId(),
                passwordRequest
        );
    }

    @GET
    @Path("/me")
    public TokenResponse getMe(
            @HeaderParam(HttpHeaders.AUTHORIZATION) String authorization
    ) {
        return accountController.authenticateWithToken(authorization);
    }
}
