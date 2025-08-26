package sistema_clinica.repository.relacional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sistema_clinica.model.relacional.Especializado;
import sistema_clinica.model.relacional.EspecializadoId;

import java.util.List;

@Repository
public interface EspecializadoRepository extends JpaRepository<Especializado, EspecializadoId> {
    List<Especializado> findByIdMedicoId(Integer medicoId);
    List<Especializado> findByIdEspecialidadeId(Integer especialidadeId);

}
