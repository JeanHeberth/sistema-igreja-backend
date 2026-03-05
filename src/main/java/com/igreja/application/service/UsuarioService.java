package com.igreja.application.service;

import com.igreja.domain.model.Usuario;
import com.igreja.domain.repository.UsuarioRepositorio;

import java.util.Optional;
import java.util.UUID;

/**
 * Serviço de aplicação para operações com Usuário.
 */
public class UsuarioService {

    private final UsuarioRepositorio usuarioRepositorio;

    public UsuarioService(UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    public Usuario cadastrar(Usuario usuario) {
        usuarioRepositorio.salvar(usuario);
        return usuario;
    }

    public Optional<Usuario> buscarPorId(UUID id) {
        return usuarioRepositorio.findById(id);
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepositorio.findByEmail(email);
    }
}

