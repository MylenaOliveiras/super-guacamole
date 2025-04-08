package org.acme.vitima;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.acme.crime.Crime;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Vitima extends PanacheEntity {

    @NotBlank(message = "O nome da vítima é obrigatório")
    @Size(min = 2, message = "O nome deve ter no mínimo 2 caracteres")
    @Schema(description = "Nome da vítima", example = "Chloe Jason")
    public String nomeCompleto;

    @Schema(description = "Idade atual da vítima", example = "2")
    public Number idade;

    @Schema(description = "Genero da Vítima", example = "2")
    public GENDER genero;

    @ManyToMany(mappedBy = "vitimas", fetch = FetchType.LAZY)
    @JsonIgnore
    public List<Crime> crimes = new ArrayList<>();
}
