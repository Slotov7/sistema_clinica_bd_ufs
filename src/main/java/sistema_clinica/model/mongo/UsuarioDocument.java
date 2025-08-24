package sistema_clinica.model.mongo;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import sistema_clinica.model.TipoUsuario;

@Data
@Document(collection = "usuario")
public class UsuarioDocument {

    @Id
    private String id;

    private String nome;
    private String username;
    private String email;
    private String senha;
    private String telefone;
    private TipoUsuario tipoUsuario;
}
