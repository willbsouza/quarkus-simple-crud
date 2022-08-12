package com.compass.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.time.Instant;

@Provider
public class ResourceExceptionHandler implements ExceptionMapper<ObjectNotFoundException> {

    @Override
    public Response toResponse(ObjectNotFoundException exception) {

        StandardError error = new StandardError();
        error.setTimestamp(Instant.now());
        error.setStatus(404);
        error.setError("Not found.");
        error.setMessage(exception.getMessage());

        return Response.status(404).entity(error).build();
    }
}
