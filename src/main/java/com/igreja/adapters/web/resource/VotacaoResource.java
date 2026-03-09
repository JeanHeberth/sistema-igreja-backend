package com.igreja.adapters.web.resource;

import com.igreja.application.service.VotacaoService;
import com.igreja.domain.model.Voto;
import com.igreja.adapters.web.mapper.VotoMapper;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Path("/votacoes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class VotacaoResource {

    @Inject
    VotacaoService votacaoService;

    @POST
    public Response registrarVoto(VotoRequest request) {
        UUID id = UUID.randomUUID();
        Voto voto = new Voto(
                id,
                request.getHinoId(),
                request.getUsuarioId(),
                request.getCultoId(),
                Instant.now()
        );
        Voto salvo = votacaoService.registrarVoto(voto);
        VotoResponse response = VotoMapper.toResponse(salvo);
        return Response.created(URI.create("/votacoes/" + salvo.getId()))
                .entity(response)
                .build();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") UUID id) {
        Optional<Voto> votoOpt = votacaoService.buscarPorId(id);
        if (votoOpt.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(VotoMapper.toResponse(votoOpt.get())).build();
    }

    @GET
    @Path("/por-culto/{cultoId}")
    public Response listarPorCulto(@PathParam("cultoId") UUID cultoId) {
        List<Voto> votos = votacaoService.listarPorCulto(cultoId);
        List<VotoResponse> respostas = votos.stream()
                .map(VotoMapper::toResponse)
                .toList();
        return Response.ok(respostas).build();
    }

    @GET
    @Path("/por-hino/{hinoId}")
    public Response listarPorHino(@PathParam("hinoId") UUID hinoId) {
        List<Voto> votos = votacaoService.listarPorHino(hinoId);
        List<VotoResponse> respostas = votos.stream()
                .map(VotoMapper::toResponse)
                .toList();
        return Response.ok(respostas).build();
    }

    public static class VotoRequest {
        private UUID usuarioId;
        private UUID hinoId;
        private UUID cultoId;

        public UUID getUsuarioId() { return usuarioId; }
        public void setUsuarioId(UUID usuarioId) { this.usuarioId = usuarioId; }

        public UUID getHinoId() { return hinoId; }
        public void setHinoId(UUID hinoId) { this.hinoId = hinoId; }

        public UUID getCultoId() { return cultoId; }
        public void setCultoId(UUID cultoId) { this.cultoId = cultoId; }
    }

    public static class VotoResponse {
        private UUID id;
        private UUID usuarioId;
        private UUID hinoId;
        private UUID cultoId;
        private Instant criadoEm;

        public VotoResponse(UUID id, UUID usuarioId, UUID hinoId, UUID cultoId, Instant criadoEm) {
            this.id = id;
            this.usuarioId = usuarioId;
            this.hinoId = hinoId;
            this.cultoId = cultoId;
            this.criadoEm = criadoEm;
        }

        public UUID getId() { return id; }
        public UUID getUsuarioId() { return usuarioId; }
        public UUID getHinoId() { return hinoId; }
        public UUID getCultoId() { return cultoId; }
        public Instant getCriadoEm() { return criadoEm; }
    }
}
