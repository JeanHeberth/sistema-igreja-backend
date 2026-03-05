package com.igreja.adapters.web.dto;

import java.time.Instant;
import java.util.UUID;

/**
 * DTO de saída para hinos.
 */
public class HinoResponse {

    private UUID id;
    private String titulo;
    private String autor;
    private String letra;
    private String urlMidia;
    private UUID coralId;
    private UUID cultoId;
    private UUID submetidoPor;
    private String status;
    private int votos;
    private Instant criadoEm;
    private Instant atualizadoEm;

    public HinoResponse() {
    }

    public HinoResponse(UUID id,
                        String titulo,
                        String autor,
                        String letra,
                        String urlMidia,
                        UUID coralId,
                        UUID cultoId,
                        UUID submetidoPor,
                        String status,
                        int votos,
                        Instant criadoEm,
                        Instant atualizadoEm) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.letra = letra;
        this.urlMidia = urlMidia;
        this.coralId = coralId;
        this.cultoId = cultoId;
        this.submetidoPor = submetidoPor;
        this.status = status;
        this.votos = votos;
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

    public String getUrlMidia() {
        return urlMidia;
    }

    public void setUrlMidia(String urlMidia) {
        this.urlMidia = urlMidia;
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

    public UUID getSubmetidoPor() {
        return submetidoPor;
    }

    public void setSubmetidoPor(UUID submetidoPor) {
        this.submetidoPor = submetidoPor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getVotos() {
        return votos;
    }

    public void setVotos(int votos) {
        this.votos = votos;
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

