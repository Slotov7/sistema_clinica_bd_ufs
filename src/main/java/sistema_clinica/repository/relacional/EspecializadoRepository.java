package sistema_clinica.repository.relacional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sistema_clinica.model.relacional.Especializado;
import sistema_clinica.model.relacional.EspecializadoId;

import java.util.List;

@Repository
public interface EspecializadoRepository extends JpaRepository<Especializado, EspecializadoId> {

    @Query("SELECT e FROM Especializado e JOIN FETCH e.medico m JOIN FETCH m.usuario JOIN FETCH e.especialidade WHERE m.id = :medicoId")
    List<Especializado> findByIdMedicoIdWithDetails(@Param("medicoId") Integer medicoId);

    @Query("SELECT e FROM Especializado e JOIN FETCH e.medico m JOIN FETCH m.usuario JOIN FETCH e.especialidade")
    List<Especializado> findAllWithDetails();

    List<Especializado> findByIdMedicoId(Integer medicoId);
    List<Especializado> findByIdEspecialidadeId(Integer especialidadeId);

}
