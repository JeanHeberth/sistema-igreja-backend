package com.igreja.adapters.web.resource;

import com.igreja.adapters.web.mapper.EventoMapper;
import com.igreja.adapters.web.record.request.EventoRequest;
import com.igreja.adapters.web.record.response.EventoResponse;
import com.igreja.application.service.EventoService;
import com.igreja.domain.model.Evento;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Path("/eventos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EventoResource {
    @Inject
    EventoService eventoService;

    @POST
    @Transactional
    @RolesAllowed({"ADMIN"})
    public Response criar(EventoRequest request) {
        Evento evento = EventoMapper.toDomain(request);
        Evento salvo = eventoService.criar(evento);
        EventoResponse response = EventoMapper.toResponse(salvo);
        return Response.created(URI.create("/eventos/" + salvo.getId())).entity(response).build();
    }

    @GET
    public List<EventoResponse> listar() {
        return eventoService.listarTodos().stream()
                .map(EventoMapper::toResponse)
                .collect(Collectors.toList());
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") UUID id) {
        return eventoService.buscarPorId(id)
                .map(evento -> Response.ok(EventoMapper.toResponse(evento)).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @RolesAllowed({"ADMIN"})
    public Response atualizar(@PathParam("id") UUID id, EventoRequest request) {
        Evento evento = EventoMapper.toDomain(request);
        evento.setId(id);
        Evento atualizado = eventoService.atualizar(evento);
        return Response.ok(EventoMapper.toResponse(atualizado)).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @RolesAllowed({"ADMIN"})
    public Response remover(@PathParam("id") UUID id) {
        eventoService.remover(id);
        return Response.noContent().build();
    }
}

