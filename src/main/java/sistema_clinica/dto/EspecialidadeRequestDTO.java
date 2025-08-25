package sistema_clinica.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EspecialidadeRequestDTO{
    @NotBlank(message = "O nome da especialidade não pode ser vazio.")
    @Size(max = 100, message = "O nome não pode exceder 100 caracteres.")
    private String nome;

    private String descricao;

    @Size(max = 20, message = "O CBO não pode exceder 20 caracteres.")
    private String codCbo;
}