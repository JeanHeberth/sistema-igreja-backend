package com.igreja.adapters.web.record.request;

import java.util.UUID;

/**
 * DTO de entrada para criação de hinos.
 */
public record HinoRequest(
    String titulo,
    String autor,
    String letra,
    String urlMidia,
    UUID coralId,
    UUID cultoId,
    UUID submetidoPor
) {}
