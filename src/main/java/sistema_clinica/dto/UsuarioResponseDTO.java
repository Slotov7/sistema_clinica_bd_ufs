package sistema_clinica.dto;

import sistema_clinica.model.TipoUsuario;
import sistema_clinica.model.mongo.UsuarioDocument;
import sistema_clinica.model.relacional.Usuario;

import lombok.Data;
@Data
public class UsuarioResponseDTO {

    private String id;
    private String nome;
    private String username;
    private String email;
    private String telefone;
    private TipoUsuario tipoUsuario;

    public UsuarioResponseDTO(Usuario usuario) {
        this.id = String.valueOf(usuario.getId());
        this.nome = usuario.getNome();
        this.username = usuario.getUsername();
        this.email = usuario.getEmail();
        this.telefone = usuario.getTelefone();
        this.tipoUsuario = usuario.getTipoUsuario();
    }
    public UsuarioResponseDTO(UsuarioDocument usuarioDocument) {
        this.id = usuarioDocument.getId();
        this.nome = usuarioDocument.getNome();
        this.username = usuarioDocument.getUsername();
        this.email = usuarioDocument.getEmail();
        this.telefone = usuarioDocument.getTelefone();
        this.tipoUsuario = usuarioDocument.getTipoUsuario();
    }


}