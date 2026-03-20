package com.igreja.adapters.web.record.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record EventoResponse(
    UUID id,
    String nome,
    String descricao,
    LocalDateTime dataHora,
    String local
) {}

