package com.igreja.domain.model;

import com.igreja.domain.enums.HinoStatus;

import java.time.Instant;
import java.util.UUID;

public class Hino {
    private UUID id;
    private String titulo;
    private String autor;
    private String letra;
    private String midiaUrl;
    private UUID submetidoPor;
    private UUID coralId;
    private UUID cultoId;
    private HinoStatus status;
    private int quantidadeVotos;
    private Instant criadoEm;
    private Instant atualizadoEm;

    public Hino() {
    }

    public Hino(UUID id, String titulo, String autor, String letra, String midiaUrl,
                UUID submetidoPor, UUID coralId, UUID cultoId, HinoStatus status,
                int quantidadeVotos, Instant criadoEm, Instant atualizadoEm) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.letra = letra;
        this.midiaUrl = midiaUrl;
        this.submetidoPor = submetidoPor;
        this.coralId = coralId;
        this.cultoId = cultoId;
        this.status = status;
        this.quantidadeVotos = quantidadeVotos;
        this.criadoEm = criadoEm;
        this.atualizadoEm = atualizadoEm;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getLetra() {
        return letra;
    }

    public void setLetra(String letra) {
        this.letra = letra;
    }

    public String getMidiaUrl() {
        return midiaUrl;
    }

    public void setMidiaUrl(String midiaUrl) {
        this.midiaUrl = midiaUrl;
    }

    public UUID getSubmetidoPor() {
        return submetidoPor;
    }

    public void setSubmetidoPor(UUID submetidoPor) {
        this.submetidoPor = submetidoPor;
    }

    public UUID getCoralId() {
        return coralId;
    }

    public void setCoralId(UUID coralId) {
        this.coralId = coralId;
    }

    public UUID getCultoId() {
        return cultoId;
    }

    public void setCultoId(UUID cultoId) {
        this.cultoId = cultoId;
    }

    public HinoStatus getStatus() {
        return status;
    }

    public void setStatus(HinoStatus status) {
        this.status = status;
    }

    public int getQuantidadeVotos() {
        return quantidadeVotos;
    }

    public void setQuantidadeVotos(int quantidadeVotos) {
        this.quantidadeVotos = quantidadeVotos;
    }

    public Instant getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(Instant criadoEm) {
        this.criadoEm = criadoEm;
    }

    public Instant getAtualizadoEm() {
        return atualizadoEm;
    }

    public void setAtualizadoEm(Instant atualizadoEm) {
        this.atualizadoEm = atualizadoEm;
    }
}

