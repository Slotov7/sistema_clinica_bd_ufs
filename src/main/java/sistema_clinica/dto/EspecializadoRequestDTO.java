package sistema_clinica.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EspecializadoRequestDTO {
    @NotNull(message = "O ID do médico é obrigatório.")
    private Integer medicoId;

    @NotNull(message = "O ID da especialidade é obrigatório.")
    private Integer especialidadeId;
}