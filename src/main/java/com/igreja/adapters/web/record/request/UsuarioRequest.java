package com.igreja.adapters.web.record.request;

import java.util.Set;
import java.util.UUID;

public record UsuarioRequest(
    String nome,
    String email,
    String senha,
    Set<String> papeis,
    UUID coralId
) {}
