package sistema_clinica.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import sistema_clinica.model.mongo.EspecialidadeDocument;

import java.util.Optional;

public interface EspecialidadeMongoRepository extends MongoRepository<EspecialidadeDocument, String> {
    Optional<EspecialidadeDocument> findByNome(String nome);
}
