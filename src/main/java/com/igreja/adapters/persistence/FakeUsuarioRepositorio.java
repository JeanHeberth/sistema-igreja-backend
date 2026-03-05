package com.igreja.adapters.persistence;

import com.igreja.domain.model.Usuario;
import com.igreja.domain.repository.UsuarioRepositorio;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Implementação em memória simples de UsuarioRepositorio, usada por enquanto
 * apenas para satisfazer a injeção de dependência e permitir subir o contexto
 * do Quarkus em testes e em desenvolvimento.
 */
@ApplicationScoped
public class FakeUsuarioRepositorio implements UsuarioRepositorio {

    private final Map<UUID, Usuario> storage = new ConcurrentHashMap<>();

    @Override
    public Optional<Usuario> findById(UUID id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public Optional<Usuario> findByEmail(String email) {
        return storage.values().stream()
                .filter(u -> email != null && email.equals(u.getEmail()))
                .findFirst();
    }

    @Override
    public void salvar(Usuario usuario) {
        if (usuario.getId() == null) {
            throw new IllegalArgumentException("Usuário deve ter ID definido antes de salvar");
        }
        storage.put(usuario.getId(), usuario);
    }
}

