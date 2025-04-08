package org.acme.sentenca;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.acme.serialKiller.SerialKiller;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.LocalDate;

@Entity
public class Sentenca extends PanacheEntity {

    @NotBlank(message = "A descrição da sentença é obrigatória")
    @Size(min = 2, message = "A descrição deve ter no mínimo 10 caracteres")
    @Schema(description = "Descrição da Sentença", example = "Prisão perpétua sem direito à condicional")
    public String descricao;

    @NotNull(message = "A data da sentença é obrigatória")
    @Schema(description = "Data que a sentença foi definida", example = "2002-03-01")
    public LocalDate data;

    @OneToOne
    @Schema(description = "Serial Killer que recebeu a sentença")
    public SerialKiller serialKiller;
}
