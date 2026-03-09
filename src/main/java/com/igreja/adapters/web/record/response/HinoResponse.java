package com.igreja.adapters.web.record.response;

import java.time.Instant;
import java.util.UUID;

/**
 * DTO de saída para hinos.
 */
public record HinoResponse(
    UUID id,
    String titulo,
    String autor,
    String letra,
    String urlMidia,
    UUID coralId,
    UUID cultoId,
    UUID submetidoPor,
    String status,
    int votos,
    Instant criadoEm,
    Instant atualizadoEm
) {}
