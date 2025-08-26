package sistema_clinica.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AdicionarEspecialidadeRequestDTO {

    @NotBlank(message = "O ID da especialidade é obrigatório.")
    private String especialidadeId;

}
