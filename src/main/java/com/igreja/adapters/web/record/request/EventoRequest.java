package com.igreja.adapters.web.record.request;

import java.time.LocalDateTime;

public record EventoRequest(
    String nome,
    String descricao,
    LocalDateTime dataHora,
    String local
) {}

