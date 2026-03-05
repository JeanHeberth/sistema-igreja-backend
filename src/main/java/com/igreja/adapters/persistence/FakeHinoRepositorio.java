package com.igreja.adapters.persistence;

import com.igreja.domain.model.Hino;
import com.igreja.domain.repository.HinoRepositorio;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Implementação em memória de HinoRepositorio para desenvolvimento e testes.
 */
@ApplicationScoped
public class FakeHinoRepositorio implements HinoRepositorio {

    private final Map<UUID, Hino> storage = new ConcurrentHashMap<>();

    @Override
    public Optional<Hino> findById(UUID id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Hino> findByCoralId(UUID coralId) {
        return storage.values().stream()
                .filter(h -> coralId != null && coralId.equals(h.getCoralId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Hino> findByCultoId(UUID cultoId) {
        return storage.values().stream()
                .filter(h -> cultoId != null && cultoId.equals(h.getCultoId()))
                .collect(Collectors.toList());
    }

    @Override
    public void salvar(Hino hino) {
        if (hino.getId() == null) {
            throw new IllegalArgumentException("Hino deve ter ID definido antes de salvar");
        }
        storage.put(hino.getId(), hino);
    }
}

