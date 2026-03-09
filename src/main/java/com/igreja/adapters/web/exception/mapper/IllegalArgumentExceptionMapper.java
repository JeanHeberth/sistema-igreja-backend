package com.igreja.adapters.web.exception.mapper;

import com.igreja.adapters.web.record.response.ApiErroResponse;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import jakarta.ws.rs.core.UriInfo;

@Provider
public class IllegalArgumentExceptionMapper implements ExceptionMapper<IllegalArgumentException> {

    @Context
    UriInfo uriInfo;

    @Override
    public Response toResponse(IllegalArgumentException exception) {
        ApiErroResponse erro = new ApiErroResponse(
            Response.Status.BAD_REQUEST.getStatusCode(),
            Response.Status.BAD_REQUEST.getReasonPhrase(),
            exception.getMessage() != null ? exception.getMessage() : "Parâmetro inválido",
             uriInfo.getPath()
        );
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(erro)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}