package sistema_clinica.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import sistema_clinica.model.mongo.EspecialidadeDocument;
import sistema_clinica.model.relacional.Especialidade;

@Data
@NoArgsConstructor
public class EspecialidadeResponseDTO {
    private String id;
    private String descricao;
    private String nome;
    private String codCbo;

    public EspecialidadeResponseDTO(Especialidade especialidade) {
        this.id = String.valueOf(especialidade.getId());
        this.descricao = especialidade.getDescricao();
        this.nome = especialidade.getNome();
        this.codCbo = especialidade.getCodCbo();
    }
    public EspecialidadeResponseDTO(EspecialidadeDocument especialidadeDocument) {
        this.id = especialidadeDocument.getId();
        this.descricao = especialidadeDocument.getDescricao();
        this.nome = especialidadeDocument.getNome();
        this.codCbo = especialidadeDocument.getCodCbo();
    }
}
