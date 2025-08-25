package sistema_clinica.model.relacional;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "especializado")
public class Especializado {

    @EmbeddedId
    private EspecializadoId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("medicoId")
    @JoinColumn(name = "medico_id")
    private Medico medico;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("especialidadeId")
    @JoinColumn(name = "especialidade_id")
    private Especialidade especialidade;
}
