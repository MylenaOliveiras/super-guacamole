package org.acme.vitima;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.List;

@Schema(name = "VitimaCreate")
public class VitimaCreateDTO {

    @NotBlank(message = "O nome da vítima é obrigatório")
    @Size(min = 2, message = "O nome deve ter no mínimo 2 caracteres")
    @Schema(description = "Nome da vítima", example = "Chloe Jason")
    public String nomeCompleto;

    @Schema(description = "Idade da vítima", example = "25")
    public Number idade;

    @Schema(description = "Genero da vitima")
    public GENDER genero;

    @Schema(description = "IDs dos crimes vinculados à vítima", example = "[1, 2, 3]")
    public List<Long> crimes;
}
