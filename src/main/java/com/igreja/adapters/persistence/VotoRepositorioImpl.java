package com.igreja.adapters.persistence;

import com.igreja.domain.model.Voto;
import com.igreja.domain.repository.VotoRepositorio;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
public class VotoRepositorioImpl implements VotoRepositorio {

    private final Map<UUID, Voto> banco = new HashMap<>();

    @Override
    public Optional<Voto> findById(UUID id) {
        return Optional.ofNullable(banco.get(id));
    }

    @Override
    public List<Voto> findByCultoId(UUID cultoId) {
        return banco.values().stream()
                .filter(voto -> cultoId.equals(voto.getCultoId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Voto> findByHinoId(UUID hinoId) {
        return banco.values().stream()
                .filter(voto -> hinoId.equals(voto.getHinoId()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean existePorUsuarioIdEHinoIdECultoId(UUID usuarioId, UUID hinoId, UUID cultoId) {
        return banco.values().stream()
                .anyMatch(voto ->
                        usuarioId.equals(voto.getUsuarioId())
                                && hinoId.equals(voto.getHinoId())
                                && cultoId.equals(voto.getCultoId()));
    }

    @Override
    public void salvar(Voto voto) {
        banco.put(voto.getId(), voto);
    }
}