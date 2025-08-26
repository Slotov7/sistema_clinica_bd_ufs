package sistema_clinica.repository.relacional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sistema_clinica.model.relacional.Medico;
import sistema_clinica.model.relacional.Usuario;

import java.util.Optional;

@Repository
public interface MedicoRepository extends JpaRepository<Medico,Integer> {
}
