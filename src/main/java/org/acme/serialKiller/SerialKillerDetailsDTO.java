package org.acme.serialKiller;


import org.acme.crime.CrimeSimpleDTO;
import org.acme.sentenca.SentencaDTO;

import java.util.List;


public record SerialKillerDetailsDTO(
        Long id,
        String nomeCompleto,
        String alcunha,
        String nacionalidade,
        Boolean ativo,
        List<CrimeSimpleDTO> crimes,
        SentencaDTO sentenca
) {
    public static SerialKillerDetailsDTO fromEntity(SerialKiller sk) {
        return new SerialKillerDetailsDTO(
                sk.id,
                sk.nomeCompleto,
                sk.alcunha,
                sk.nacionalidade,
                sk.ativo,
                CrimeSimpleDTO.fromList(sk.crimes),
                sk.sentenca != null ? SentencaDTO.fromEntity(sk.sentenca) : null

        );
    }
}


