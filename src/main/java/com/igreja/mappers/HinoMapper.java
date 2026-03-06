package com.igreja.mappers;

import com.igreja.domain.model.Hino;
import com.igreja.adapters.web.record.request.HinoResponse;
import com.igreja.domain.enums.HinoStatus;

public class HinoMapper {
    public static HinoResponse toResponse(Hino hino) {
        return new HinoResponse(
                hino.getId(),
                hino.getTitulo(),
                hino.getAutor(),
                hino.getLetra(),
                hino.getMidiaUrl(),
                hino.getCoralId(),
                hino.getCultoId(),
                hino.getSubmetidoPor(),
                hino.getStatus().name(),
                0, // ajustar quando o domínio expuser o getter de votos
                hino.getCriadoEm(),
                hino.getAtualizadoEm()
        );
    }

    public static Hino toEntity(HinoResponse response) {
        HinoStatus status = response.status() != null ? HinoStatus.valueOf(response.status()) : null;
        return new Hino(
                response.id(),
                response.titulo(),
                response.autor(),
                response.letra(),
                response.urlMidia(),
                response.submetidoPor(),
                response.coralId(),
                response.cultoId(),
                status,
                response.votos(),
                response.criadoEm(),
                response.atualizadoEm()
        );
    }
}
