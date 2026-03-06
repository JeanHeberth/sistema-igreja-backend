package com.igreja.adapters.web.resource;

import com.igreja.application.service.EventoCultoService;
import com.igreja.domain.model.EventoCulto;
import com.igreja.adapters.web.record.response.EventoCultoResponse;
import com.igreja.adapters.web.mapper.EventoCultoMapper;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Path("/eventos-culto")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EventoCultoResource {

    @Inject
    EventoCultoService eventoCultoService;

    @POST
    public Response agendar(EventoCultoRequest request) {
        UUID id = UUID.randomUUID();
        Instant dataHora = request.dataHora() != null ? request.dataHora().atZone(java.time.ZoneId.systemDefault()).toInstant() : null;
        EventoCulto evento = new EventoCulto(
                id,
                dataHora,
                request.titulo(),
                null,
                List.of()
        );
        EventoCulto salvo = eventoCultoService.agendar(evento);
        EventoCultoResponse response = EventoCultoMapper.toResponse(salvo);
        return Response.created(URI.create("/eventos-culto/" + salvo.getId()))
                .entity(response)
                .build();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") UUID id) {
        Optional<EventoCulto> eventoOpt = eventoCultoService.buscarPorId(id);
        if (eventoOpt.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(EventoCultoMapper.toResponse(eventoOpt.get())).build();
    }

    @GET
    public Response listarTodos() {
        List<EventoCulto> eventos = eventoCultoService.listarTodos();
        List<EventoCultoResponse> respostas = eventos.stream()
                .map(EventoCultoMapper::toResponse)
                .toList();
        return Response.ok(respostas).build();
    }

    public record EventoCultoRequest(
        String titulo,
        String descricao,
        LocalDateTime dataHora,
        UUID coralId
    ) {}
}
