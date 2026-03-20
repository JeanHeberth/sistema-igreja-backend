package com.igreja.application.service;

import com.igreja.domain.model.Evento;
import com.igreja.domain.repository.EventoRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class EventoService {
    @Inject
    EventoRepository eventoRepository;

    public Evento criar(Evento evento) {
        return eventoRepository.save(evento);
    }

    public Optional<Evento> buscarPorId(UUID id) {
        return eventoRepository.findById(id);
    }

    public List<Evento> listarTodos() {
        return eventoRepository.findAll();
    }

    public void remover(UUID id) {
        eventoRepository.deleteById(id);
    }

    public Evento atualizar(Evento evento) {
        return eventoRepository.update(evento);
    }
}

