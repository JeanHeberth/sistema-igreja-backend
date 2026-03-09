package com.igreja.adapters.web.exception.mapper;

import com.igreja.adapters.web.exception.DadosInvalidosException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.Map;

@Provider
public class DadosInvalidosExceptionMapper implements ExceptionMapper<DadosInvalidosException> {

    @Override
    public Response toResponse(DadosInvalidosException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(Map.of("message", exception.getMessage()))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
