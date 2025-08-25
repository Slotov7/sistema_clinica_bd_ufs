package sistema_clinica.dto;

import lombok.Data;
import sistema_clinica.model.relacional.Especialidade;

@Data
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
}
