package com.igreja.adapters.web;

import com.igreja.adapters.web.record.request.HinoRequest;
import com.igreja.adapters.web.record.response.HinoResponse;
import com.igreja.application.service.HinoService;
import com.igreja.domain.enums.HinoStatus;
import com.igreja.domain.model.Hino;
import com.igreja.adapters.web.mapper.HinoMapper;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Path("/hinos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class HinoResource {

    @Inject
    HinoService hinoService;

    @POST
    public Response criarHino(HinoRequest request) {
        UUID id = UUID.randomUUID();
        Instant agora = Instant.now();

        Hino hino = new Hino(
                id,
                request.titulo(),
                request.autor(),
                request.letra(),
                request.urlMidia(),
                request.submetidoPor(),
                request.coralId(),
                request.cultoId(),
                HinoStatus.PENDENTE,
                0,
                agora,
                agora
        );

        Hino salvo = hinoService.cadastrar(hino);
        HinoResponse response = HinoMapper.toResponse(salvo);

        return Response.created(URI.create("/hinos/" + salvo.getId()))
                .entity(response)
                .build();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") UUID id) {
        Optional<Hino> hinoOpt = hinoService.buscarPorId(id);
        if (hinoOpt.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(HinoMapper.toResponse(hinoOpt.get())).build();
    }

    @GET
    @Path("/por-coral/{coralId}")
    public Response listarPorCoral(@PathParam("coralId") UUID coralId) {
        List<Hino> hinos = hinoService.listarPorCoral(coralId);
        List<HinoResponse> response = hinos.stream()
                .map(HinoMapper::toResponse)
                .collect(Collectors.toList());
        return Response.ok(response).build();
    }

    @GET
    @Path("/por-culto/{cultoId}")
    public Response listarPorCulto(@PathParam("cultoId") UUID cultoId) {
        List<Hino> hinos = hinoService.listarPorCulto(cultoId);
        List<HinoResponse> response = hinos.stream()
                .map(HinoMapper::toResponse)
                .collect(Collectors.toList());
        return Response.ok(response).build();
    }

}
