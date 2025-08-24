package sistema_clinica.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import sistema_clinica.model.TipoUsuario;

@Data
public class UsuarioRequestDTO {

    @NotBlank(message = "O nome é obrigatório.")
    private String nome;

    @NotBlank(message = "O username é obrigatório.")
    private String username;

    @NotBlank(message = "O email é obrigatório.")
    @Email(message = "O formato do email é inválido.")
    private String email;

    @NotBlank(message = "A senha é obrigatória.")
    @Size(min = 6, max = 20, message = "A senha deve ter entre 6 e 20 caracteres.")
    private String senha;

    private String telefone;

    @NotNull(message = "O tipo de usuário é obrigatório.")
    private TipoUsuario tipoUsuario;


}