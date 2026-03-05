package com.igreja.adapters.web.dto;

import java.util.UUID;

/**
 * DTO de entrada para criação de hinos.
 */
public class HinoRequest {

    private String titulo;
    private String autor;
    private String letra;
    private String urlMidia;
    private UUID coralId;
    private UUID cultoId;
    private UUID submetidoPor;

    public HinoRequest() {
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
}

