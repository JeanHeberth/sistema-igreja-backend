package com.igreja.adapters.web.exception.mapper;

import com.igreja.adapters.web.exception.DadosInvalidosException;
import com.igreja.adapters.web.record.response.ApiErroResponse;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import jakarta.ws.rs.core.UriInfo;

@Provider
public class DadosInvalidosExceptionMapper implements ExceptionMapper<DadosInvalidosException> {

    @Context
    UriInfo uriInfo;

    @Override
    public Response toResponse(DadosInvalidosException exception) {
        ApiErroResponse erro = new ApiErroResponse(
            Response.Status.BAD_REQUEST.getStatusCode(),
            Response.Status.BAD_REQUEST.getReasonPhrase(),
            exception.getMessage(),
            uriInfo.getPath()
        );
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(erro)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
