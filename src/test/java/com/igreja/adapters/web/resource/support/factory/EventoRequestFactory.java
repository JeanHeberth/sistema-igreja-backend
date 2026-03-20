package com.igreja.adapters.web.resource.support.factory;

import com.igreja.adapters.web.record.request.EventoRequest;

import java.time.LocalDateTime;

public class EventoRequestFactory {
    public static EventoRequest novoEventoRequestPadrao() {
        return new EventoRequest(
                "Culto de Domingo",
                "Culto principal da semana",
                LocalDateTime.now().plusDays(1),
                "Templo Central"
        );
    }

    public static EventoRequest novoEventoRequestComNome(String nome) {
        return new EventoRequest(
                nome,
                "Evento especial",
                LocalDateTime.now().plusDays(2),
                "Salão Social"
        );
    }
}

