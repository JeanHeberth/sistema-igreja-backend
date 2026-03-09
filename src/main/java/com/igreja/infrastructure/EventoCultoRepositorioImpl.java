package com.igreja.infrastructure;

import com.igreja.domain.model.EventoCulto;
import com.igreja.domain.repository.EventoCultoRepositorio;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.*;

@ApplicationScoped
public class EventoCultoRepositorioImpl implements EventoCultoRepositorio {

    private final Map<UUID, EventoCulto> banco = new HashMap<>();

    @Override
    public Optional<EventoCulto> findById(UUID id) {
        return Optional.ofNullable(banco.get(id));
    }

    @Override
    public List<EventoCulto> findAll() {
        return new ArrayList<>(banco.values());
    }

    @Override
    public void salvar(EventoCulto eventoCulto) {
        banco.put(eventoCulto.getId(), eventoCulto);
    }
}