package org.acme.sentenca;

import java.time.LocalDate;

public record SentencaDTO(Long id, String descricao, LocalDate data, long serialKillerId) {
    public static SentencaDTO fromEntity(Sentenca sentenca) {
        return new SentencaDTO(sentenca.id, sentenca.descricao, sentenca.data, sentenca.serialKiller.id);
    }
}
