package com.igreja.application.service;

import com.igreja.adapters.web.support.factory.DomainTestFactory;
import com.igreja.domain.model.EventoCulto;
import com.igreja.domain.repository.EventoCultoRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EventoCultoServiceTest {

    private EventoCultoRepositorio eventoCultoRepositorio;
    private EventoCultoService eventoCultoService;

    @BeforeEach
    void setUp() {
        eventoCultoRepositorio = mock(EventoCultoRepositorio.class);
        eventoCultoService = new EventoCultoService(eventoCultoRepositorio);
    }

    @Test
    void agendar_deveSalvarEDevolverEvento() {
        EventoCulto evento = DomainTestFactory.novoEventoCultoDefault();

        EventoCulto resultado = eventoCultoService.agendar(evento);

        assertSame(evento, resultado);
        verify(eventoCultoRepositorio, times(1)).salvar(evento);
    }

    @Test
    void buscarPorId_deveDelegarParaRepositorio() {
        UUID id = UUID.randomUUID();
        EventoCulto evento = DomainTestFactory.novoEventoCultoDefault();
        when(eventoCultoRepositorio.findById(id)).thenReturn(Optional.of(evento));

        Optional<EventoCulto> resultado = eventoCultoService.buscarPorId(id);

        assertTrue(resultado.isPresent());
        assertSame(evento, resultado.get());
        verify(eventoCultoRepositorio, times(1)).findById(id);
    }

    @Test
    void listarTodos_deveDelegarParaRepositorio() {
        List<EventoCulto> lista = List.of(
                DomainTestFactory.novoEventoCultoDefault(),
                DomainTestFactory.novoEventoCultoDefault()
        );
        when(eventoCultoRepositorio.findAll()).thenReturn(lista);

        List<EventoCulto> resultado = eventoCultoService.listarTodos();

        assertEquals(lista, resultado);
        verify(eventoCultoRepositorio, times(1)).findAll();
    }
}

