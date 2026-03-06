package com.igreja.adapters.web.mapper;


import com.igreja.adapters.web.record.response.EventoCultoResponse;
import com.igreja.domain.model.EventoCulto;

public class EventoCultoMapper {
    public static EventoCultoResponse toResponse(EventoCulto entity) {
        if (entity == null) return null;
        return new EventoCultoResponse(
            entity.getId(),
            entity.getDataHora(), // Corrigido para getDataHora()
            entity.getNome(),
            entity.getStatus() != null ? entity.getStatus().name() : null,
            entity.getHinosSelecionados()
        );
    }
}
