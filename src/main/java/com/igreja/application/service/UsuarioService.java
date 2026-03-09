package com.igreja.application.service;

import com.igreja.adapters.web.exception.RecursoDuplicadoException;
import com.igreja.domain.model.Usuario;
import com.igreja.domain.repository.UsuarioRepositorio;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Optional;
import java.util.UUID;

/**
 * Serviço de aplicação para operações com Usuário.
 */
@ApplicationScoped
public class UsuarioService {

    private final UsuarioRepositorio usuarioRepositorio;

    @Inject
    public UsuarioService(UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    public Usuario cadastrar(Usuario usuario) {
        boolean salvou = usuarioRepositorio.salvarSeEmailNaoExistir(usuario);

        if (!salvou) {
            throw new RecursoDuplicadoException("Email já cadastrado");
        }
        return usuario;
    }

    public Optional<Usuario> buscarPorId(UUID id) {
        return usuarioRepositorio.findById(id);
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepositorio.findByEmail(email);
    }
}
