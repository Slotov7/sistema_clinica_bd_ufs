package sistema_clinica.repository.mongo;

import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;
import sistema_clinica.model.mongo.UsuarioDocument;

import java.util.Optional;


public interface UsuarioMongoRepository extends MongoRepository<UsuarioDocument, String> {
    Optional<UsuarioDocument> findByUsername(String username);
}
