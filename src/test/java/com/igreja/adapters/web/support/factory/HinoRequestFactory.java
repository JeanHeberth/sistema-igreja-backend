package com.igreja.adapters.web.support.factory;

import com.igreja.adapters.web.record.request.HinoRequest;

import java.util.UUID;

public final class HinoRequestFactory {

    private HinoRequestFactory() {
    }

    public static HinoRequest valido() {
        return new HinoRequest(
                "Hino Teste",
                "Compositor Teste",
                "Letra do hino teste",
                "https://midia.example.com/hino.mp3",
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID()
        );
    }
}
