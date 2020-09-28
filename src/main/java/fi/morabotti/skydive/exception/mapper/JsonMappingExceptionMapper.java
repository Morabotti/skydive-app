package fi.morabotti.skydive.exception.mapper;

import com.fasterxml.jackson.databind.JsonMappingException;
import fi.jubic.snoozy.mappers.exceptions.ExceptionView;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class JsonMappingExceptionMapper implements ExceptionMapper<JsonMappingException> {
    private final boolean isDevelopment;

    public JsonMappingExceptionMapper(boolean isDevelopment) {
        this.isDevelopment = isDevelopment;
    }

    @Override
    public Response toResponse(JsonMappingException e) {
        return Response.status(Response.Status.BAD_REQUEST).entity(
                new ExceptionView(
                        Response.Status.BAD_REQUEST.getStatusCode(),
                        isDevelopment ? e.getMessage() : "Could not parse JSON"
                )
        ).build();
    }
}

