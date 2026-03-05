package com.igreja.adapters.web;

import com.igreja.adapters.web.dto.UsuarioRequest;
import com.igreja.adapters.web.dto.UsuarioResponse;
import com.igreja.application.service.UsuarioService;
import com.igreja.domain.enums.Papel;
import com.igreja.domain.model.Usuario;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Path("/usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioResource {

    @Inject
    UsuarioService usuarioService;

    @POST
    public Response criarUsuario(UsuarioRequest request) {
        UUID id = UUID.randomUUID();
        Set<Papel> papeis = request.getPapeis() == null ? Set.of()
                : request.getPapeis().stream()
                .map(String::toUpperCase)
                .map(Papel::valueOf)
                .collect(Collectors.toSet());

        Usuario usuario = new Usuario(
                id,
                request.getNome(),
                request.getEmail(),
                request.getSenha(),
                papeis,
                request.getCoralId()
        );

        Usuario salvo = usuarioService.cadastrar(usuario);

        UsuarioResponse response = new UsuarioResponse(
                salvo.getId(),
                salvo.getNome(),
                salvo.getEmail(),
                salvo.getPapeis().stream().map(Enum::name).collect(Collectors.toSet()),
                salvo.getCoralId()
        );

        return Response.created(URI.create("/usuarios/" + salvo.getId()))
                .entity(response)
                .build();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") UUID id) {
        Optional<Usuario> usuarioOpt = usuarioService.buscarPorId(id);
        if (usuarioOpt.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Usuario usuario = usuarioOpt.get();
        UsuarioResponse response = new UsuarioResponse(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getPapeis().stream().map(Enum::name).collect(Collectors.toSet()),
                usuario.getCoralId()
        );

        return Response.ok(response).build();
    }

    @GET
    @Path("/por-email")
    public Response buscarPorEmail(@QueryParam("email") String email) {
        Optional<Usuario> usuarioOpt = usuarioService.buscarPorEmail(email);
        if (usuarioOpt.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Usuario usuario = usuarioOpt.get();
        UsuarioResponse response = new UsuarioResponse(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getPapeis().stream().map(Enum::name).collect(Collectors.toSet()),
                usuario.getCoralId()
        );

        return Response.ok(response).build();
    }
}

