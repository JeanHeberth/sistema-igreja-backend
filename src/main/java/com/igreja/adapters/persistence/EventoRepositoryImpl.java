package com.igreja.adapters.persistence;

import com.igreja.domain.model.Evento;
import com.igreja.domain.repository.EventoRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class EventoRepositoryImpl implements EventoRepository {
    private final Map<UUID, Evento> store = new ConcurrentHashMap<>();

    @Override
    public Evento save(Evento evento) {
        UUID id = evento.getId() != null ? evento.getId() : UUID.randomUUID();
        evento.setId(id);
        store.put(id, evento);
        return evento;
    }

    @Override
    public Optional<Evento> findById(UUID id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Evento> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void deleteById(UUID id) {
        store.remove(id);
    }

    @Override
    public Evento update(Evento evento) {
        if (evento.getId() == null || !store.containsKey(evento.getId())) {
            throw new NoSuchElementException("Evento não encontrado para atualização");
        }
        store.put(evento.getId(), evento);
        return evento;
    }
}

