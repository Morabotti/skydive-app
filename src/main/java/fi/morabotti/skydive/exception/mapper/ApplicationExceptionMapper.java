package fi.morabotti.skydive.exception.mapper;

import fi.jubic.snoozy.mappers.exceptions.ExceptionView;
import fi.morabotti.skydive.exception.ApplicationException;
import fi.morabotti.skydive.exception.AuthenticationException;
import fi.morabotti.skydive.exception.BadOperationException;
import fi.morabotti.skydive.exception.NotFoundException;
import fi.morabotti.skydive.exception.PasswordException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class ApplicationExceptionMapper implements ExceptionMapper<ApplicationException> {
    @Override
    public Response toResponse(ApplicationException e) {
        Response.Status status = getResponseStatus(e);
        return Response.status(status)
                .entity(
                        new ExceptionView(
                                status.getStatusCode(),
                                e.getMessage()
                        )
                ).build();
    }

    private Response.Status getResponseStatus(ApplicationException exception) {
        if (exception instanceof AuthenticationException) return Response.Status.UNAUTHORIZED;
        if (exception instanceof NotFoundException) return Response.Status.NOT_FOUND;
        if (exception instanceof PasswordException) return Response.Status.INTERNAL_SERVER_ERROR;
        if (exception instanceof BadOperationException) return Response.Status.BAD_REQUEST;

        throw new IllegalArgumentException("Unsupported exception");
    }
}
