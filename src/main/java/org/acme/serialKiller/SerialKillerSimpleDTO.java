package org.acme.serialKiller;

public record SerialKillerSimpleDTO(Long id, String nome) {
    public static SerialKillerSimpleDTO fromEntity(SerialKiller sk) {
        return new SerialKillerSimpleDTO(sk.id, sk.nomeCompleto);
    }
}

