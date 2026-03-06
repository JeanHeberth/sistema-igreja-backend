package com.igreja.mappers;

import com.igreja.domain.model.Usuario;
import com.igreja.adapters.web.record.request.UsuarioResponse;
import java.util.stream.Collectors;

public class UsuarioMapper {
    public static UsuarioResponse toResponse(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getPapeis().stream().map(Enum::name).collect(Collectors.toSet()),
                usuario.getCoralId()
        );
    }
}

