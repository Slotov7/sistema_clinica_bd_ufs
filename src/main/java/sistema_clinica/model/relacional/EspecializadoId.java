package sistema_clinica.model.relacional;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class EspecializadoId implements Serializable {

    @Column(name = "medico_id")
    private Integer medicoId;

    @Column(name = "especialidade_id")
    private Integer especialidadeId;

}
