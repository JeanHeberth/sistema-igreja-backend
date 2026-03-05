package com.igreja.domain.model;

import com.igreja.domain.enums.Papel;

import java.util.Set;
import java.util.UUID;

public class Usuario {
    private UUID id;
    private String nome;
    private String email;
    private String senhaHash;
    private Set<Papel> papeis;
    private UUID coralId;

    public Usuario() {
    }

    public Usuario(UUID id, String nome, String email, String senhaHash, Set<Papel> papeis, UUID coralId) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senhaHash = senhaHash;
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

    public String getSenhaHash() {
        return senhaHash;
    }

    public void setSenhaHash(String senhaHash) {
        this.senhaHash = senhaHash;
    }

    public Set<Papel> getPapeis() {
        return papeis;
    }

    public void setPapeis(Set<Papel> papeis) {
        this.papeis = papeis;
    }

    public UUID getCoralId() {
        return coralId;
    }

    public void setCoralId(UUID coralId) {
        this.coralId = coralId;
    }
}

