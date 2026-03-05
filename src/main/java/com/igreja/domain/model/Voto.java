package com.igreja.domain.model;

import java.time.Instant;
import java.util.UUID;

public class Voto {
    private UUID id;
    private UUID hinoId;
    private UUID usuarioId;
    private UUID cultoId;
    private Instant criadoEm;

    public Voto() {
    }

    public Voto(UUID id, UUID hinoId, UUID usuarioId, UUID cultoId, Instant criadoEm) {
        this.id = id;
        this.hinoId = hinoId;
        this.usuarioId = usuarioId;
        this.cultoId = cultoId;
        this.criadoEm = criadoEm;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getHinoId() {
        return hinoId;
    }

    public void setHinoId(UUID hinoId) {
        this.hinoId = hinoId;
    }

    public UUID getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(UUID usuarioId) {
        this.usuarioId = usuarioId;
    }

    public UUID getCultoId() {
        return cultoId;
    }

    public void setCultoId(UUID cultoId) {
        this.cultoId = cultoId;
    }

    public Instant getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(Instant criadoEm) {
        this.criadoEm = criadoEm;
    }
}

