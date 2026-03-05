package com.igreja.domain.repository;

import com.igreja.domain.model.Usuario;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepositorio {
    Optional<Usuario> findById(UUID id);
    Optional<Usuario> findByEmail(String email);
    void salvar(Usuario usuario);
}

