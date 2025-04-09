package org.acme.crime;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.acme.serialKiller.SerialKiller;
import org.acme.vitima.Vitima;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Schema(description = "Entidade que representa um crime registrado no sistema.")
public class Crime extends PanacheEntity {

    @NotBlank(message = "O nome do crime é obrigatório")
    @Size(min = 2, message = "O nome deve ter no mínimo 2 caracteres")
    @Schema(description = "Nome do crime", example = "Furto Qualificado")
    public String nome;

    @NotBlank(message = "A descrição do crime é obrigatória")
    @Size(min = 5, message = "A descrição deve ter no mínimo 5 caracteres")
    @Schema(description = "Descrição detalhada do crime", example = "Furto de veículo durante a madrugada")
    public String descricao;

    @NotNull(message = "A data do crime é obrigatória")
    @Schema(description = "Data em que o crime ocorreu (formato: dd/MM/yyyy)", example = "2002-03-01")
    public LocalDate data;

    @NotBlank(message = "O local do crime é obrigatório")
    @Schema(description = "Local onde o crime ocorreu", example = "Rua das Flores, 123 - São Paulo")
    public String local;

    @NotNull(message = "O campo arquivado é obrigatório")
    @Schema(description = "Indica se o crime foi arquivado (true = sim, false = não)", example = "false")
    public Boolean arquivado = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "serial_killer_id")
    public SerialKiller serialKiller;

    @ManyToMany(mappedBy = "crimes", fetch = FetchType.LAZY)
    public List<Vitima> vitimas = new ArrayList<>();
}
