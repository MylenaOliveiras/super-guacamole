package org.acme.crime;

import java.util.List;

public record CrimeSimpleDTO(Long id, String nome) {
    public static CrimeSimpleDTO fromEntity(Crime crime) {
        return new CrimeSimpleDTO(crime.id, crime.nome);
    }

    public static List<CrimeSimpleDTO> fromList(List<Crime> crimes) {
        return crimes.stream()
                .map(CrimeSimpleDTO::fromEntity)
                .toList();
    }
}
