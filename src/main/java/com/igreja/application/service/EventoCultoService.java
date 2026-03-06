package com.igreja.application.service;

import com.igreja.domain.model.EventoCulto;
import com.igreja.domain.repository.EventoCultoRepositorio;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Serviço de aplicação para operações com Evento de Culto.
 */
@ApplicationScoped
public class EventoCultoService {

    private final EventoCultoRepositorio eventoCultoRepositorio;

    public EventoCultoService(EventoCultoRepositorio eventoCultoRepositorio) {
        this.eventoCultoRepositorio = eventoCultoRepositorio;
    }

    public EventoCulto agendar(EventoCulto eventoCulto) {
        eventoCultoRepositorio.salvar(eventoCulto);
        return eventoCulto;
    }

    public Optional<EventoCulto> buscarPorId(UUID id) {
        return eventoCultoRepositorio.findById(id);
    }

    public List<EventoCulto> listarTodos() {
        return eventoCultoRepositorio.findAll();
    }
}
