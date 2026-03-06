package com.igreja.adapters.web.record.response;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record EventoCultoResponse(
    UUID id,
    Instant dataHora,
    String nome,
    String status,
    List<UUID> hinosSelecionados
) {}
