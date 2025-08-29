package sistema_clinica.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import sistema_clinica.model.TipoUsuario;
import sistema_clinica.model.mongo.UsuarioDocument;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioMongoRepository extends MongoRepository<UsuarioDocument, String> {
    Optional<UsuarioDocument> findByUsername(String username);

    List<UsuarioDocument> findByTipoUsuario(TipoUsuario tipoUsuario);

    Optional<UsuarioDocument> findByIdAndTipoUsuario(String id, TipoUsuario tipoUsuario);

    List<UsuarioDocument> findByTipoUsuarioAndEspecialidadeIdsContains(TipoUsuario tipo, String especialidadeId);
}

