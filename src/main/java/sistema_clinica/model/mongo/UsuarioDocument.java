package sistema_clinica.model.mongo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import sistema_clinica.model.TipoUsuario;

import java.util.ArrayList;
import java.util.List;

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

    private String crm;

    @Field("especialidadeIds") // Mapeia a relação "especializado"
    private List<String> especialidadeIds = new ArrayList<>();
}
