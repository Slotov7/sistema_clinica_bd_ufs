package sistema_clinica.dto;

import lombok.Data;
import sistema_clinica.model.relacional.Especializado;

@Data
public class EspecializadoResponseDTO {
    private Integer medicoId;
    private String nomeMedico;
    private Integer especialidadeId;
    private String nomeEspecialidade;

    public EspecializadoResponseDTO(Especializado especializado) {
        this.medicoId = Math.toIntExact(especializado.getMedico().getId());
        this.nomeMedico = especializado.getMedico().getUsuario().getNome();
        this.especialidadeId = Math.toIntExact(especializado.getEspecialidade().getId());
        this.nomeEspecialidade = especializado.getEspecialidade().getNome();
    }

}
