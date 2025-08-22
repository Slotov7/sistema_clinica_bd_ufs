package sistema_clinica.dto;

import sistema_clinica.model.TipoUsuario;
import sistema_clinica.model.Usuario;

import lombok.Data;
@Data
public class UsuarioResponseDTO {

    private int id;
    private String nome;
    private String username;
    private String email;
    private String telefone;
    private TipoUsuario tipoUsuario;

    public UsuarioResponseDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.username = usuario.getUsername();
        this.email = usuario.getEmail();
        this.telefone = usuario.getTelefone();
        this.tipoUsuario = usuario.getTipoUsuario();
    }

}