package com.igreja.adapters.web.resource;

import com.igreja.adapters.web.record.request.UsuarioRequest;
import com.igreja.adapters.web.record.response.UsuarioResponse;
import com.igreja.application.service.UsuarioService;
import com.igreja.domain.model.Usuario;
import com.igreja.adapters.web.mapper.UsuarioMapper;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@Path("/usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioResource {

    @Inject
    UsuarioService usuarioService;

    @POST
    @RolesAllowed("ADMIN")
    public Response criarUsuario(@Valid UsuarioRequest request) {
        Usuario usuario = UsuarioMapper.toDomain(request);
        Usuario salvo = usuarioService.cadastrar(usuario);

        UsuarioResponse response = UsuarioMapper.toResponse(salvo);

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

        return Response.ok(UsuarioMapper.toResponse(usuarioOpt.get())).build();
    }

    @GET
    @Path("/por-email")
    public Response buscarPorEmail(@QueryParam("email") String email) {
        Optional<Usuario> usuarioOpt = usuarioService.buscarPorEmail(email);
        if (usuarioOpt.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(UsuarioMapper.toResponse(usuarioOpt.get())).build();
    }

    @GET
    @RolesAllowed({"USER", "ADMIN"})
    public Response listarUsuarios() {
        return Response.ok().build();
    }
}