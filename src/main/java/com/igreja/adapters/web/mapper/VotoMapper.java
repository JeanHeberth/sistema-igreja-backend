package com.igreja.adapters.web.mapper;

import com.igreja.domain.model.Voto;
import com.igreja.adapters.web.VotacaoResource.VotoResponse;

public class VotoMapper {
    public static VotoResponse toResponse(Voto voto) {
        return new VotoResponse(
                voto.getId(),
                voto.getUsuarioId(),
                voto.getHinoId(),
                voto.getCultoId(),
                voto.getCriadoEm()
        );
    }
}

