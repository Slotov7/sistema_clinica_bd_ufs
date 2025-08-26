package sistema_clinica.dto;

import jakarta.validation.constraints.NotNull;
import sistema_clinica.model.relacional.Medico;

public record MedicoDTO(
        @NotNull(message = "O ID do usuário é obrigatório para criar um médico.")
        Integer usuarioId,

        String nomeUsuario,
        @NotNull(message = "O CRM é obrigatório.")
        Integer crm
) {
    public MedicoDTO(Medico medico) {
        this(
                medico.getId(),
                medico.getUsuario().getNome(),
                medico.getCrm()
        );
    }
}