package com.igreja.adapters.web.exception.mapper;

import com.igreja.adapters.web.exception.RecursoDuplicadoException;
import com.igreja.adapters.web.record.response.ApiErroResponse;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import jakarta.ws.rs.core.UriInfo;

@Provider
public class RecursoDuplicadoExceptionMapper implements ExceptionMapper<RecursoDuplicadoException> {

    @Context
    UriInfo uriInfo;

    @Override
    public Response toResponse(RecursoDuplicadoException exception) {
        ApiErroResponse erro = new ApiErroResponse(
            Response.Status.CONFLICT.getStatusCode(),
            Response.Status.CONFLICT.getReasonPhrase(),
            exception.getMessage(),
             uriInfo.getPath()
        );
        return Response.status(Response.Status.CONFLICT)
                .entity(erro)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}