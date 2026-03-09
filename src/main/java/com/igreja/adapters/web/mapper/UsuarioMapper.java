package com.igreja.adapters.web.mapper;

import com.igreja.adapters.web.exception.DadosInvalidosException;
import com.igreja.adapters.web.record.request.UsuarioRequest;
import com.igreja.adapters.web.record.response.UsuarioResponse;
import com.igreja.domain.enums.Papel;
import com.igreja.domain.model.Usuario;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class UsuarioMapper {

    private UsuarioMapper() {
    }

    public static Usuario toDomain(UsuarioRequest request) {
        Set<Papel> papeis;
        try {
            papeis = request.papeis() == null ? Set.of()
                    : request.papeis().stream()
                    .map(String::toUpperCase)
                    .map(Papel::valueOf)
                    .collect(Collectors.toSet());
        } catch (IllegalArgumentException e) {
            throw new DadosInvalidosException("Papel inválido");
        }

        return new Usuario(
                UUID.randomUUID(),
                request.nome(),
                request.email(),
                request.senha(),
                papeis,
                request.coralId()
        );
    }

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