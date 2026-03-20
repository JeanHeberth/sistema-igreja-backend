package com.igreja.adapters.web.mapper;

import com.igreja.adapters.web.record.request.EventoRequest;
import com.igreja.adapters.web.record.response.EventoResponse;
import com.igreja.domain.model.Evento;

public class EventoMapper {
    public static Evento toDomain(EventoRequest request) {
        return new Evento(null, request.nome(), request.descricao(), request.dataHora(), request.local());
    }

    public static EventoResponse toResponse(Evento evento) {
        return new EventoResponse(evento.getId(), evento.getNome(), evento.getDescricao(), evento.getDataHora(), evento.getLocal());
    }
}

