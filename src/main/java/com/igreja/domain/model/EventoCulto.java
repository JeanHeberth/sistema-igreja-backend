package com.igreja.domain.model;

import com.igreja.domain.enums.CultoStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventoCulto {
    private UUID id;
    private Instant dataHora;
    private String nome;
    private CultoStatus status;
    private List<UUID> hinosSelecionados;
}
