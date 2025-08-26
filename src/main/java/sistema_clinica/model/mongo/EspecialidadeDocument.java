package sistema_clinica.model.mongo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "especialidades")
@Data
public class EspecialidadeDocument {

    @Id
    private String id;

    @Indexed(unique = true)
    private String nome;

    private String descricao;

    private String codCbo;

}
