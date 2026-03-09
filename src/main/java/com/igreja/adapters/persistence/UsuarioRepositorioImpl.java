package com.igreja.adapters.persistence;

import com.igreja.domain.model.Usuario;
import com.igreja.domain.repository.UsuarioRepositorio;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class UsuarioRepositorioImpl implements UsuarioRepositorio {

    private final Map<UUID, Usuario> storage = new ConcurrentHashMap<>();

    @Override
    public Optional<Usuario> findById(UUID id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public Optional<Usuario> findByEmail(String email) {
        return storage.values().stream()
                .filter(usuario -> Objects.equals(usuario.getEmail(), email))
                .findFirst();
    }

    @Override
    public void salvar(Usuario usuario) {
        storage.put(usuario.getId(), usuario);
    }

    @Override
    public synchronized boolean salvarSeEmailNaoExistir(Usuario usuario) {
        boolean emailJaExiste = storage.values().stream()
                .anyMatch(u -> Objects.equals(u.getEmail(), usuario.getEmail()));

        if (emailJaExiste) {
            return false;
        }

        storage.put(usuario.getId(), usuario);
        return true;
    }
}