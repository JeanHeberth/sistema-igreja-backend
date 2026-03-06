package com.igreja.adapters.web.record.request;

import java.util.Set;
import java.util.UUID;

public record UsuarioResponse(
    UUID id,
    String nome,
    String email,
    Set<String> papeis,
    UUID coralId
) {}
