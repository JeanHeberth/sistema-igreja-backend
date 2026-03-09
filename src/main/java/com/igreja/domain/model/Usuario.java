package com.igreja.domain.model;

import com.igreja.domain.enums.Papel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
    private UUID id;
    private String nome;
    private String email;
    private String senha;
    private Set<Papel> papeis;
    private UUID coralId;

}