package com.igreja.adapters.web.exception.mapper;

import com.igreja.adapters.web.exception.RecursoDuplicadoException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.Map;

@Provider
public class RecursoDuplicadoExceptionMapper implements ExceptionMapper<RecursoDuplicadoException> {

    @Override
    public Response toResponse(RecursoDuplicadoException exception) {
        return Response.status(Response.Status.CONFLICT)
                .entity(Map.of("message", exception.getMessage()))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}