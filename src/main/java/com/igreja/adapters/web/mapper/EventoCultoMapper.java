package com.igreja.adapters.web.mapper;

import com.igreja.adapters.web.record.response.EventoCultoResponse;
import com.igreja.domain.model.EventoCulto;
import com.igreja.domain.enums.CultoStatus;

public class EventoCultoMapper {
    public static EventoCultoResponse toResponse(EventoCulto eventoCulto) {
        return new EventoCultoResponse(
            eventoCulto.getId(),
            eventoCulto.getDataHora(),
            eventoCulto.getNome(),
            eventoCulto.getStatus() != null ? eventoCulto.getStatus().name() : null,
            eventoCulto.getHinosSelecionados()
        );
    }

    public static EventoCulto toEntity(EventoCultoResponse response) {
        CultoStatus status = response.status() != null ? CultoStatus.valueOf(response.status()) : null;
        return EventoCulto.builder()
                .id(response.id())
                .dataHora(response.dataHora())
                .nome(response.nome())
                .status(status)
                .hinosSelecionados(response.hinosSelecionados())
                .build();
    }
}
