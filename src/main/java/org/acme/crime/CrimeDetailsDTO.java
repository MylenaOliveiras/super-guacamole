package org.acme.crime;

import org.acme.vitima.Vitima;
import org.acme.vitima.VitimaDetailsDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public record CrimeDetailsDTO(
        Long id,
        String nome,
        String descricao,
        LocalDate data,
        String local,
        Boolean arquivado,
        Long serialKillerId,
        List<VitimaDetailsDTO> vitimas
) {
    public static CrimeDetailsDTO fromEntity(Crime crime) {
        return new CrimeDetailsDTO(
                crime.id,
                crime.nome,
                crime.descricao,
                crime.data,
                crime.local,
                crime.arquivado,
                crime.serialKiller != null ? crime.serialKiller.id : null,
                crime.vitimas.stream().map(v -> new VitimaDetailsDTO(
                        v.id,
                        v.nomeCompleto,
                        v.genero,
                        v.idade,
                        v.crimes.stream().map(c -> c.id).collect(Collectors.toList())
                ))
                .collect(Collectors.toList())

        );
    }
}
