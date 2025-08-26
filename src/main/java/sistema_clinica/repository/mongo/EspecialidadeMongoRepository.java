package sistema_clinica.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import sistema_clinica.model.mongo.EspecialidadeDocument;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface EspecialidadeMongoRepository extends MongoRepository<EspecialidadeDocument, String> {
    Optional<EspecialidadeDocument> findByNome(String nome);
}
