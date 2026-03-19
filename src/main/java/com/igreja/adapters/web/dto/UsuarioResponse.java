package com.igreja.adapters.web.dto;

import java.util.Set;
import java.util.UUID;

/**
 * DTO de saída para usuário.
 */
public class UsuarioResponse {

    private UUID id;
    private String nome;
    private String email;
    private Set<String> papeis;
    private UUID coralId;

    public UsuarioResponse() {
    }

    public UsuarioResponse(UUID id, String nome, String email, Set<String> papeis, UUID coralId) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.papeis = papeis;
        this.coralId = coralId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<String> getPapeis() {
        return papeis;
    }

    public void setPapeis(Set<String> papeis) {
        this.papeis = papeis;
    }

    public UUID getCoralId() {
        return coralId;
    }

    public void setCoralId(UUID coralId) {
        this.coralId = coralId;
    }
}

