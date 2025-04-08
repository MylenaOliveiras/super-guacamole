package org.acme.vitima;


import java.util.List;


public class VitimaDetailsDTO {
    public Long id;
    public String nomeCompleto;
    public GENDER genero;
    public Number idade;
    public List<Long> crimes;

    public VitimaDetailsDTO(Long id, String nomeCompleto, GENDER genero, Number idade, List<Long> crimes) {
        this.id = id;
        this.nomeCompleto = nomeCompleto;
        this.genero = genero;
        this.idade = idade;
        this.crimes = crimes;
    }
}



