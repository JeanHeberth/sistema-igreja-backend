package com.igreja.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.Objects;

public class Evento {
    private UUID id;
    private String nome;
    private String descricao;
    private LocalDateTime dataHora;
    private String local;

    public Evento() {}

    public Evento(UUID id, String nome, String descricao, LocalDateTime dataHora, String local) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.dataHora = dataHora;
        this.local = local;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }
    public String getLocal() { return local; }
    public void setLocal(String local) { this.local = local; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Evento evento = (Evento) o;
        return Objects.equals(id, evento.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

