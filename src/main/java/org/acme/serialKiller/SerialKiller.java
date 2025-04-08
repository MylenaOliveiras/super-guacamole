package org.acme.serialKiller;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.acme.crime.Crime;
import org.acme.sentenca.Sentenca;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.ArrayList;
import java.util.List;

@Entity
@Schema(description = "Entidade que representa um Serial Killer registrado no sistema.")
public class SerialKiller extends PanacheEntity {

    @NotBlank(message = "O nome do Serial Killer é obrigatório")
    @Size(min = 2, message = "O nome deve ter no mínimo 2 caracteres")
    @Schema(description = "Nome do Serial Killer", example = "Jeffrey Dahmer")
    public String nomeCompleto;

    @Schema(description = "O nome que esse individuo ficou popularmente conhecido", example = "Canibal de Milwaukee")
    public String alcunha;

    @NotBlank(message = "Nacionalidade do individuo é obrigatória")
    @Schema(description = "Local de origem desse individuo")
    public String nacionalidade;

    @NotNull(message = "O campo arquivado é obrigatório")
    @Schema(description = "Indica se o crime foi arquivado", example = "false")
    public Boolean ativo = true;

    @OneToMany(mappedBy = "serialKiller", cascade = CascadeType.ALL)
    @Schema(description = "Crimes vinculado a esse SK")
    public List<Crime> crimes = new ArrayList<>();

    @OneToOne(mappedBy = "serialKiller", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Schema(description = "Sentença recebida")
    public Sentenca sentenca;
}
