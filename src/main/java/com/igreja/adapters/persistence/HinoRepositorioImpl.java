package com.igreja.adapters.persistence;


import com.igreja.domain.model.Hino;
import com.igreja.domain.repository.HinoRepositorio;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@ApplicationScoped
public class HinoRepositorioImpl implements HinoRepositorio {

    private final Map<UUID, Hino> storage = new ConcurrentHashMap<>();

    @Override
    public Optional<Hino> findById(UUID id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Hino> findByCoralId(UUID coralId) {
        return storage.values().stream()
                .filter(hino -> coralId != null && coralId.equals(hino.getCoralId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Hino> findByCultoId(UUID cultoId) {
        return storage.values().stream()
                .filter(hino -> cultoId != null && cultoId.equals(hino.getCultoId()))
                .collect(Collectors.toList());
    }

    @Override
    public void salvar(Hino hino) {
        storage.put(hino.getId(), hino);
    }
}
