package org.acme.crime;

import org.acme.crime.Crime;
import org.acme.serialKiller.SerialKiller;

import java.time.LocalDate;

public record CrimeCreateDTO(
        String nome,
        String descricao,
        LocalDate data,
        String local,
        Boolean arquivado,
        Long serialKillerId
) {
    public Crime toEntity(SerialKiller serialKiller) {
        Crime crime = new Crime();
        crime.nome = this.nome;
        crime.descricao = this.descricao;
        crime.data = this.data;
        crime.local = this.local;
        crime.arquivado = false;
        crime.serialKiller = serialKiller;
        return crime;
    }
}
