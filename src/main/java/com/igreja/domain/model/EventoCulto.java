package com.igreja.domain.model;

import com.igreja.domain.enums.CultoStatus;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class EventoCulto {
    private UUID id;
    private Instant dataHora;
    private String nome;
    private CultoStatus status;
    private List<UUID> hinosSelecionados;

    public EventoCulto() {
    }

    public EventoCulto(UUID id, Instant dataHora, String nome, CultoStatus status, List<UUID> hinosSelecionados) {
        this.id = id;
        this.dataHora = dataHora;
        this.nome = nome;
        this.status = status;
        this.hinosSelecionados = hinosSelecionados;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Instant getDataHora() {
        return dataHora;
    }

    public void setDataHora(Instant dataHora) {
        this.dataHora = dataHora;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public CultoStatus getStatus() {
        return status;
    }

    public void setStatus(CultoStatus status) {
        this.status = status;
    }

    public List<UUID> getHinosSelecionados() {
        return hinosSelecionados;
    }

    public void setHinosSelecionados(List<UUID> hinosSelecionados) {
        this.hinosSelecionados = hinosSelecionados;
    }
}

